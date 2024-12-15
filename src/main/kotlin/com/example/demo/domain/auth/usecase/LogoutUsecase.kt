package com.example.demo.domain.auth.usecase

import com.example.demo.domain.auth.dto.request.SignInRequest
import com.example.demo.domain.auth.dto.request.VerifyEmailRequest
import com.example.demo.domain.auth.dto.response.SignInResponse
import com.example.demo.domain.auth.repository.RefreshTokenRepository
import com.example.demo.domain.auth.repository.VerifyCodeRepository
import com.example.demo.domain.user.repository.UserRepository
import com.example.demo.global.exception.ExceptionEnum
import com.example.demo.global.exception.NoNameException
import com.example.demo.global.security.jwt.JwtProvider
import com.example.demo.global.security.jwt.JwtType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class LogoutUsecase (
	private val refreshTokenRepository: RefreshTokenRepository,
	private val jwtProvider: JwtProvider
) {
	fun execute(resolveRefreshToken: String) {
		val savedRefreshToken = jwtProvider.getSavedRefreshTokenByRefreshToken(resolveRefreshToken)

		if(resolveRefreshToken != savedRefreshToken.refreshToken){
			throw NoNameException(ExceptionEnum.INVALID_REFRESH_TOKEN)
		}

		refreshTokenRepository.delete(savedRefreshToken)
	}
}