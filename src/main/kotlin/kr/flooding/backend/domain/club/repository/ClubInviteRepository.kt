package kr.flooding.backend.domain.club.repository

import kr.flooding.backend.domain.club.entity.ClubInvite
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ClubInviteRepository : CrudRepository<ClubInvite, UUID>
