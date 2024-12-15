package com.example.demo.domain.auth.repository

import com.example.demo.domain.auth.entity.RefreshToken
import com.example.demo.domain.auth.entity.VerifyCode
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface VerifyCodeRepository: CrudRepository<VerifyCode, UUID>