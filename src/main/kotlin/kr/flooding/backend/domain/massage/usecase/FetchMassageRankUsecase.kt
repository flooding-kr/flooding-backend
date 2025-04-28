package kr.flooding.backend.domain.massage.usecase

import kr.flooding.backend.domain.massage.dto.response.FetchMassageRankListResponse
import kr.flooding.backend.domain.massage.dto.response.FetchMassageRankResponse
import kr.flooding.backend.domain.massage.persistence.repository.jdsl.MassageReservationJdslRepository
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import kr.flooding.backend.global.util.DateUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchMassageRankUsecase(
	private val massageReservationJdslRepository: MassageReservationJdslRepository,
	private val s3Adapter: S3Adapter,
) {
	fun execute(): FetchMassageRankListResponse {
		val reservations = massageReservationJdslRepository.findByCreatedAtBetweenAndIsCancelledAndOrderByCreatedAtDesc(
			DateUtil.getAtStartOfToday(),
			DateUtil.getAtEndOfToday(),
			false
		)

		val responses = reservations.map {
			val user = it.student
			val studentInfo = requireNotNull(user.studentInfo)
			val profileImageUrl = user.profileImageKey?.let { key ->
				s3Adapter.generatePresignedUrl(key)
			}

			FetchMassageRankResponse(
				name = user.name,
				schoolNumber = studentInfo.toSchoolNumber(),
				profileImageUrl = profileImageUrl,
			)
		}

		return FetchMassageRankListResponse(responses)
	}
}
