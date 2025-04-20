package kr.flooding.backend.domain.club.dto.web.response

import kr.flooding.backend.domain.club.dto.common.response.ClubClassroomResponse
import kr.flooding.backend.domain.club.dto.common.response.ClubStudentResponse
import kr.flooding.backend.domain.club.dto.common.response.ClubTeacherResponse
import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.club.enums.ClubStatus
import kr.flooding.backend.domain.club.enums.ClubType
import java.util.UUID

class FetchClubResponse(
	val id: UUID?,
	val name: String,
	val description: String,
	val classroom: ClubClassroomResponse,
	val activityImageUrls: List<String>,
	val thumbnailImageUrl: String?,
	val type: ClubType,
	val isRecruiting: Boolean,
	val clubMembers: List<ClubStudentResponse>,
	val status: ClubStatus,
	val leader: ClubStudentResponse,
	val teacher: ClubTeacherResponse?,
	val applicantCount: Int,
) {
	companion object {
		fun toDto(
			club: Club,
			clubMembers: List<ClubStudentResponse>,
			thumbnailImageUrl: String?,
			activityImageUrls: List<String>,
			teacher: ClubTeacherResponse?,
			leader: ClubStudentResponse,
			applicantCount: Int
		): FetchClubResponse {
			return FetchClubResponse(
				id = club.id,
				name = club.name,
				description = club.description,
				classroom = ClubClassroomResponse.toDto(club.classroom),
				activityImageUrls = activityImageUrls,
				status = club.status,
				type = club.type,
				isRecruiting = club.isRecruiting,
				clubMembers = clubMembers,
				thumbnailImageUrl = thumbnailImageUrl,
				leader = leader,
				teacher = teacher,
				applicantCount = applicantCount
			)
		}
	}
}
