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
	val clubMembers: List<ClubMemberResponse>,
	val status: ClubStatus,
	val leader: ClubMemberResponse,
	val teacher: ClubMemberResponse?,
) {
	companion object {
		fun toDto(
			club: Club,
			clubMembers: List<ClubMember>,
			thumbnailImageUrl: String?,
			activityImageUrls: List<String>,
		): FetchClubResponse {
			val teacher = if (club.teacher != null) ClubMemberResponse.toDto(club.teacher!!) else null

			return FetchClubResponse(
				id = club.id,
				name = club.name,
				description = club.description,
				classroom = ClubClassroomModel.toDto(club.classroom),
				activityImageUrls = activityImageUrls,
				status = club.status,
				type = club.type,
				isRecruiting = club.isRecruiting,
				clubMembers = clubMembers.map { ClubMemberResponse.toDto(it.user) },
				thumbnailImageUrl = thumbnailImageUrl,
				leader = ClubMemberResponse.toDto(club.leader),
				teacher = teacher,
			)
		}
	}
}
