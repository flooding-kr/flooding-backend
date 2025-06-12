package kr.flooding.backend.domain.homebaseParticipants.persistence.jpa

import kr.flooding.backend.domain.homebase.persistence.entity.HomebaseGroup
import kr.flooding.backend.domain.homebaseParticipants.persistence.entity.HomebaseParticipant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface HomebaseParticipantRepository : JpaRepository<HomebaseParticipant, UUID> {
	fun findByHomebaseGroup(homebaseGroup: HomebaseGroup?): List<HomebaseParticipant>
}
