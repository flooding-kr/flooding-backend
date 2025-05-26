package kr.flooding.backend.domain.club.usecase.student

import kr.flooding.backend.domain.club.dto.common.response.ClubMyselfResponse
import kr.flooding.backend.domain.club.dto.web.response.FetchClubMyselfResponse
import kr.flooding.backend.domain.clubMember.persistence.repository.jdsl.ClubMemberJdslRepository
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchClubMyselfUsecase(
	private val clubMemberJdslRepository: ClubMemberJdslRepository,
	private val userUtil: UserUtil,
	private val s3Adapter: S3Adapter
) {
	fun execute(): FetchClubMyselfResponse {
		val currentUser = userUtil.getUser()
		val currentClubMember = clubMemberJdslRepository.findByUser(currentUser)
		val clubs = currentClubMember.map { it.club }

		return FetchClubMyselfResponse(
			clubs.map { club ->
				val thumbnailImage = club.thumbnailImageKey?.let {
					s3Adapter.generatePresignedUrl(it)
				}
				ClubMyselfResponse.toDto(club, currentUser, thumbnailImage)
			},
		)
	}
}
