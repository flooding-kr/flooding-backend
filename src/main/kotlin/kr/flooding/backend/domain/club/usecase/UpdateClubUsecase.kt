package kr.flooding.backend.domain.club.usecase

import kr.flooding.backend.domain.classroom.repository.ClassroomRepository
import kr.flooding.backend.domain.club.dto.request.UpdateClubRequest
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
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
		requireNotNull(request.clubId)

		val currentClub =
			clubRepository.findById(request.clubId).orElseThrow {
				HttpException(ExceptionEnum.NOT_FOUND_CLUB)
			}
		val currentUser = userUtil.getUser()

		if (currentClub.leader != currentUser) {
			throw HttpException(ExceptionEnum.NOT_CLUB_LEADER)
		}

		val changedClassroom =
			request.classroomId.let {
				if (it != null) {
					classroomRepository.findById(it).orElseThrow {
						HttpException(ExceptionEnum.NOT_FOUND_CLASSROOM)
					}
				} else {
					currentClub.classroom
				}
			}

		val changedActivityImages =
			if (request.activityImageUrls?.isNotEmpty() == true) {
				request.activityImageUrls
			} else {
				currentClub.activityImageUrls
			} ?: listOf()

		val updatedClub =
			currentClub.copy(
				name = currentClub.name.updateIfNotBlank(request.name),
				description = currentClub.description.updateIfNotBlank(request.description),
				classroom = changedClassroom,
				thumbnailImageUrl = currentClub.thumbnailImageUrl?.updateIfNotBlank(request.mainImageUrl),
				activityImageUrls = changedActivityImages,
			)

		clubRepository.save(updatedClub)
	}

	fun String.updateIfNotBlank(changedValue: String?): String = changedValue?.takeIf { it.isNotBlank() } ?: this
}
