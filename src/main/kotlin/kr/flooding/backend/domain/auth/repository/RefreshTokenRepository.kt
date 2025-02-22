package kr.flooding.backend.domain.auth.repository

import kr.flooding.backend.domain.auth.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RefreshTokenRepository : CrudRepository<RefreshToken, UUID>
