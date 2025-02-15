package team.gsm.flooding.domain.auth.repository

import org.springframework.data.repository.CrudRepository
import team.gsm.flooding.domain.auth.entity.VerifyCode
import java.util.UUID

interface VerifyCodeRepository : CrudRepository<VerifyCode, UUID>
