package team.gsm.flooding.domain.club.dto.response

import team.gsm.flooding.domain.club.entity.Club
import team.gsm.flooding.domain.club.entity.ClubType
import java.util.UUID

class FindClubFilterResponse(
	val myClubs: List<ClubFilterResponse>?,
	val autonomousClubs: List<ClubFilterResponse>?,
	val majorClubs: List<ClubFilterResponse>?,
	val careerClubs: List<ClubFilterResponse>?,
) {
	companion object {
		fun toDto(
			myClubs: List<Club>,
			otherClub: Map<ClubType, List<Club>>,
		): FindClubFilterResponse =
			FindClubFilterResponse(
				myClubs = myClubs.map { ClubFilterResponse.toDto(it) },
				autonomousClubs = otherClub[ClubType.AUTONOMOUS]?.map { ClubFilterResponse.toDto(it) }.orEmpty(),
				majorClubs = otherClub[ClubType.MAJOR]?.map { ClubFilterResponse.toDto(it) }.orEmpty(),
				careerClubs = otherClub[ClubType.CAREER]?.map { ClubFilterResponse.toDto(it) }.orEmpty(),
			)
	}

	class ClubFilterResponse(
		val id: UUID?,
		val name: String,
		val thumbnailImageUrl: String?,
	) {
		companion object {
			fun toDto(club: Club): ClubFilterResponse =
				ClubFilterResponse(
					id = club.id,
					name = club.name,
					thumbnailImageUrl = club.thumbnailImageUrl,
				)
		}
	}
}
