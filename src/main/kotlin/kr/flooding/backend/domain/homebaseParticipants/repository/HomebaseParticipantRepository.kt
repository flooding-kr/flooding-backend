package kr.flooding.backend.domain.homebaseParticipants.repository

import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import kr.flooding.backend.domain.homebaseParticipants.entity.HomebaseParticipant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface HomebaseParticipantRepository : JpaRepository<HomebaseParticipant, UUID> {
	fun findByHomebaseGroup(homebaseGroup: HomebaseGroup?): List<HomebaseParticipant>
}
