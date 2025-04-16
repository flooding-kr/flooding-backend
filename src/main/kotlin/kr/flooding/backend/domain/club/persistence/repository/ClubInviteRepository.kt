package kr.flooding.backend.domain.club.persistence.repository

import kr.flooding.backend.domain.club.persistence.entity.ClubInvite
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ClubInviteRepository : CrudRepository<ClubInvite, UUID>
