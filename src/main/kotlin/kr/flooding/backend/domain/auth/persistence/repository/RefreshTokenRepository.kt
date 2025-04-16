package kr.flooding.backend.domain.auth.persistence.repository

import kr.flooding.backend.domain.auth.persistence.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RefreshTokenRepository : CrudRepository<RefreshToken, UUID>
