package kr.flooding.backend.domain.club.dto.response

import kr.flooding.backend.domain.club.model.ClubClassroomModel
import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.club.persistence.entity.ClubStatus
import kr.flooding.backend.domain.club.persistence.entity.ClubType
import kr.flooding.backend.domain.clubMember.persistence.entity.ClubMember
import java.util.UUID

class FetchClubResponse(
	val id: UUID?,
	val name: String,
	val description: String,
	val classroom: ClubClassroomModel,
	val activityImageUrls: List<String>,
	val thumbnailImageUrl: String?,
	val type: ClubType,
	val isRecruiting: Boolean,
	val clubMembers: List<ClubStudentResponse>,
	val status: ClubStatus,
	val leader: ClubStudentResponse,
	val teacher: ClubTeacherResponse?,
) {
	companion object {
		fun toDto(
			club: Club,
			clubMembers: List<ClubStudentResponse>,
			thumbnailImageUrl: String?,
			activityImageUrls: List<String>,
			teacher: ClubTeacherResponse?,
			leader: ClubStudentResponse,
		): FetchClubResponse {
			return FetchClubResponse(
				id = club.id,
				name = club.name,
				description = club.description,
				classroom = ClubClassroomModel.toDto(club.classroom),
				activityImageUrls = activityImageUrls,
				status = club.status,
				type = club.type,
				isRecruiting = club.isRecruiting,
				clubMembers = clubMembers,
				thumbnailImageUrl = thumbnailImageUrl,
				leader = leader,
				teacher = teacher,
			)
		}
	}
}
