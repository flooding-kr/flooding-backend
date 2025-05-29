package kr.flooding.backend.domain.club.usecase.student

import kr.flooding.backend.domain.club.dto.web.request.UpdateClubRequest
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.domain.homebaseTable.persistence.repository.jpa.ClassroomRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdateClubUsecase(
	private val clubRepository: ClubRepository,
	private val classroomRepository: ClassroomRepository,
	private val userUtil: UserUtil,
) {
	fun execute(request: UpdateClubRequest) {
		val clubId = checkNotNull(request.clubId) { "Club ID must not be null" }
		val currentUser = userUtil.getUser()

		val currentClub = clubRepository.findById(clubId).orElseThrow {
			HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
		}

		if (currentClub.leader != currentUser) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_LEADER.toPair())
		}

		val changedClassroom = request.classroomId?.let {
			classroomRepository.findById(it).orElseThrow {
				HttpException(ExceptionEnum.CLASSROOM.NOT_FOUND_CLASSROOM.toPair())
			}
		} ?: currentClub.classroom

		val changedActivityImages =
			if (request.activityImageKeys.isNullOrEmpty()) {
				currentClub.activityImageKeys
			} else {
				request.activityImageKeys
			}

		val updatedClub = currentClub.copy(
			classroom = changedClassroom,
			activityImageKeys = changedActivityImages,
			name = request.name?.takeIf { it.isNotBlank() } ?: currentClub.name,
			description = request.description?.takeIf { it.isNotBlank() } ?: currentClub.description,
			thumbnailImageKey = request.thumbnailImageKey?.takeIf {
				it.isNotBlank()
			} ?: currentClub.thumbnailImageKey,
		)

		clubRepository.save(updatedClub)
	}
}
