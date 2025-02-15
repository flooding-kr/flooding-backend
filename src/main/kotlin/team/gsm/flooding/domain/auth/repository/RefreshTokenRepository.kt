package team.gsm.flooding.domain.auth.repository

import org.springframework.data.repository.CrudRepository
import team.gsm.flooding.domain.auth.entity.RefreshToken
import java.util.UUID

interface RefreshTokenRepository : CrudRepository<RefreshToken, UUID>
