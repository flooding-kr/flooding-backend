package kr.flooding.backend.global.security.details

import kr.flooding.backend.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthDetailsService(
	private val userRepository: UserRepository,
) : UserDetailsService {
	// username is user's id
	override fun loadUserByUsername(username: String): UserDetails {
		val id = UUID.fromString(username)
		val userByEmail =
			userRepository.findById(id).orElseThrow {
				IllegalAccessException()
			}
		return AuthDetails(userByEmail)
	}
}
