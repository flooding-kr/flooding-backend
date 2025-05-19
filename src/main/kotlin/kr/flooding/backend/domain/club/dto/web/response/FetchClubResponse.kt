package kr.flooding.backend.domain.club.dto.web.response

import kr.flooding.backend.domain.club.dto.common.response.ClubClassroomResponse
import kr.flooding.backend.domain.club.dto.common.response.ClubStudentResponse
import kr.flooding.backend.domain.club.dto.common.response.ClubTeacherResponse
import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.club.enums.ClubStatus
import kr.flooding.backend.domain.club.enums.ClubType
import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import java.util.UUID

class FetchClubResponse(
	val id: UUID?,
	val name: String,
	val description: String,
	val classroom: ClubClassroomResponse?,
	val activityImages: List<PresignedUrlModel>,
	val thumbnailImage: PresignedUrlModel?,
	val type: ClubType,
	val isRecruiting: Boolean,
	val clubMembers: List<ClubStudentResponse>,
	val status: ClubStatus,
	val leader: ClubStudentResponse?,
	val teacher: ClubTeacherResponse?,
	val applicantCount: Int,
) {
	companion object {
		fun toDto(
			club: Club,
			clubMembers: List<ClubStudentResponse>,
			thumbnailImage: PresignedUrlModel?,
			activityImages: List<PresignedUrlModel>,
			teacher: ClubTeacherResponse?,
			leader: ClubStudentResponse?,
			applicantCount: Int
		): FetchClubResponse {
			return FetchClubResponse(
				id = club.id,
				name = club.name,
				description = club.description,
				classroom = club.classroom?.run(ClubClassroomResponse::toDto),
				activityImages = activityImages,
				status = club.status,
				type = club.type,
				isRecruiting = club.isRecruiting,
				clubMembers = clubMembers,
				thumbnailImage = thumbnailImage,
				leader = leader,
				teacher = teacher,
				applicantCount = applicantCount
			)
		}
	}
}
