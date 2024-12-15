package com.example.demo.domain.auth.usecase

import com.example.demo.domain.auth.dto.request.SignInRequest
import com.example.demo.domain.auth.dto.response.SignInResponse
import com.example.demo.domain.auth.repository.RefreshTokenRepository
import com.example.demo.domain.user.repository.UserRepository
import com.example.demo.global.exception.ExceptionEnum
import com.example.demo.global.exception.NoNameException
import com.example.demo.global.security.jwt.JwtProvider
import com.example.demo.global.security.jwt.JwtType
import com.example.demo.global.util.UserUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class LogoutUsecase (
	private val userRepository: UserRepository,
	private val passwordEncoder: PasswordEncoder,
	private val jwtProvider: JwtProvider,
	private val refreshTokenRepository: RefreshTokenRepository,
	private val userUtil: UserUtil
) {
	fun execute(signInRequest: SignInRequest) {
		val currentUser = userUtil.getUser()
		val currentUserRefreshToken = refreshTokenRepository.findById(currentUser.id).orElseThrow {
			NoNameException(ExceptionEnum.NOT_FOUND_REFRESH_TOKEN)
		}

		jwtProvider.isValidateToken(currentUserRefreshToken.refreshToken)
	}

	fun getRefreshTokenOrSave(id: String): String {
		val refreshToken = refreshTokenRepository.findById(id)

		if(refreshToken.isEmpty){
			val newRefreshToken = jwtProvider.generateRefreshTokenEntity(id)
			refreshTokenRepository.save(newRefreshToken)
			return newRefreshToken.refreshToken
		} else {
			return refreshToken.get().refreshToken
		}
	}
}