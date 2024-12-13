package com.example.demo.domain.auth.repository

import com.example.demo.domain.auth.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RefreshTokenRepository: CrudRepository<RefreshToken, UUID> {
}