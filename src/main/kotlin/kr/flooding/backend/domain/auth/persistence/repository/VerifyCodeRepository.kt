package kr.flooding.backend.domain.auth.persistence.repository

import kr.flooding.backend.domain.auth.persistence.entity.VerifyCode
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface VerifyCodeRepository : CrudRepository<VerifyCode, UUID>
