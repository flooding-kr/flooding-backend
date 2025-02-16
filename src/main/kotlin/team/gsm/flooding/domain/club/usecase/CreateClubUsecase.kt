package team.gsm.flooding.domain.club.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.classroom.repository.ClassroomRepository
import team.gsm.flooding.domain.club.dto.request.CreateClubRequest
import team.gsm.flooding.domain.club.entity.Club
import team.gsm.flooding.domain.club.entity.ClubStatus
import team.gsm.flooding.domain.club.repository.ClubRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException

@Service
@Transactional
class CreateClubUsecase(
	private val clubRepository: ClubRepository,
	private val classroomRepository: ClassroomRepository,
) {
	fun execute(createClubRequest: CreateClubRequest) {
		clubRepository.existsByName(createClubRequest.name).takeIf { it }?.let {
			throw ExpectedException(ExceptionEnum.ALREADY_USED_CLUB_NAME)
		}

		val classroom =
			classroomRepository.findById(createClubRequest.classroomId).orElseThrow {
				ExpectedException(ExceptionEnum.NOT_FOUND_CLASSROOM)
			}

		if (classroom.isHomebase) {
			throw ExpectedException(ExceptionEnum.IS_HOMEBASE_CLASSROOM)
		}

		clubRepository.save(
			Club(
				name = createClubRequest.name,
				description = createClubRequest.description,
				classroom = classroom,
				activityImageUrls = createClubRequest.activityImageUrls,
				status = ClubStatus.PENDING,
				type = createClubRequest.type,
				thumbnailImageUrl = createClubRequest.mainImageUrl,
			),
		)
	}
}
