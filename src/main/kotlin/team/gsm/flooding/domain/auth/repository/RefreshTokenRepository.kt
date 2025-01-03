package team.gsm.flooding.domain.auth.repository

import team.gsm.flooding.domain.auth.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RefreshTokenRepository: CrudRepository<RefreshToken, UUID>