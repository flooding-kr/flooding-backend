package team.gsm.flooding.global.security.details

import team.gsm.flooding.domain.user.repository.UserRepository
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthDetailsService(
	private val userRepository: UserRepository
): UserDetailsService {
	// username is user's id
	override fun loadUserByUsername(username: String): UserDetails {
		val id = UUID.fromString(username)
		val userByEmail = userRepository.findById(id).orElseThrow {
			IllegalAccessException()
		}
		return AuthDetails(userByEmail)
	}
}