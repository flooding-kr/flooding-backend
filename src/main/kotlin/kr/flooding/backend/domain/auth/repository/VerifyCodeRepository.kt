package kr.flooding.backend.domain.auth.repository

import kr.flooding.backend.domain.auth.entity.VerifyCode
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface VerifyCodeRepository : CrudRepository<VerifyCode, UUID>
