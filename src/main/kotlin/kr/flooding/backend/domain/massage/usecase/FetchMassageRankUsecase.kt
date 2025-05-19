package kr.flooding.backend.domain.massage.usecase

import kr.flooding.backend.domain.massage.dto.response.FetchMassageRankListResponse
import kr.flooding.backend.domain.massage.dto.response.FetchMassageRankResponse
import kr.flooding.backend.domain.massage.persistence.repository.jdsl.MassageReservationJdslRepository
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import kr.flooding.backend.global.util.DateUtil.Companion.atEndOfDay
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class FetchMassageRankUsecase(
	private val massageReservationJdslRepository: MassageReservationJdslRepository,
	private val s3Adapter: S3Adapter,
) {
	fun execute(): FetchMassageRankListResponse {
		val currentDate = LocalDate.now()
		val reservations = massageReservationJdslRepository.findByCreatedAtBetweenAndIsCancelledAndOrderByCreatedAtAsc(
			currentDate.atStartOfDay(),
			currentDate.atEndOfDay(),
			false
		)

		val responses = reservations.map {
			val user = it.student
			val studentInfo = requireNotNull(user.studentInfo)
			val profileImage = user.profileImageKey?.let { key ->
				s3Adapter.generatePresignedUrl(key)
			}

			FetchMassageRankResponse(
				name = user.name,
				schoolNumber = studentInfo.toSchoolNumber(),
				profileImage = profileImage,
			)
		}

		return FetchMassageRankListResponse(responses)
	}
}
