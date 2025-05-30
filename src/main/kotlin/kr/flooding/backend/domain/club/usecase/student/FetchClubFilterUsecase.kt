package kr.flooding.backend.domain.club.usecase.student

import kr.flooding.backend.domain.club.dto.common.response.ClubFilterResponse
import kr.flooding.backend.domain.club.dto.web.response.FetchClubFilterResponse
import kr.flooding.backend.domain.club.enums.ClubStatus
import kr.flooding.backend.domain.club.enums.ClubType
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchClubFilterUsecase(
	private val clubRepository: ClubRepository,
	private val userUtil: UserUtil,
	private val s3Adapter: S3Adapter
) {
	fun execute(type: ClubType?): FetchClubFilterResponse {
		val clubs =
			if (type == null) {
				clubRepository.findWithLeaderAndStatus(ClubStatus.APPROVED)
			} else {
				clubRepository.findWithLeaderByTypeAndStatus(type, ClubStatus.APPROVED)
			}

		val currentUser = userUtil.getUser()

		return FetchClubFilterResponse(
			clubs.map { club ->
				val thumbnailImage = club.thumbnailImageKey?.let {
					s3Adapter.generatePresignedUrl(it)
				}
				ClubFilterResponse.toDto(club, currentUser, thumbnailImage)
			},
		)
	}
}
