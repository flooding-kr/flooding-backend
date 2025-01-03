package team.gsm.flooding.domain.auth.repository

import team.gsm.flooding.domain.auth.entity.RefreshToken
import team.gsm.flooding.domain.auth.entity.VerifyCode
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface VerifyCodeRepository: CrudRepository<VerifyCode, UUID>