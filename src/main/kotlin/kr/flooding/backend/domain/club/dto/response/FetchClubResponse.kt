package kr.flooding.backend.domain.club.dto.response

import kr.flooding.backend.domain.classroom.entity.Classroom
import kr.flooding.backend.domain.club.entity.Club
import kr.flooding.backend.domain.club.entity.ClubStatus
import kr.flooding.backend.domain.club.entity.ClubType
import kr.flooding.backend.domain.clubMember.entity.ClubMember
import java.util.UUID

class FetchClubResponse(
	val id: UUID?,
	val name: String,
	val description: String,
	val classroom: Classroom,
	val activityImageUrls: List<String>,
	val type: ClubType,
	val isRecruiting: Boolean,
	val clubMembers: List<ClubMemberResponse>,
	val status: ClubStatus,
) {
	companion object {
		fun toDto(
			club: Club,
			clubMembers: List<ClubMember>,
		): FetchClubResponse =
			FetchClubResponse(
				id = club.id,
				name = club.name,
				description = club.description,
				classroom = club.classroom,
				activityImageUrls = club.activityImageUrls,
				status = club.status,
				type = club.type,
				isRecruiting = club.isRecruiting,
				clubMembers = clubMembers.map { ClubMemberResponse.toDto(it.user) },
			)
	}
}
