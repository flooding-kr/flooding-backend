package kr.flooding.backend.global.security.details

import kr.flooding.backend.domain.role.persistence.repository.jpa.RoleJpaRepository
import kr.flooding.backend.domain.user.persistence.repository.jdsl.UserJdslRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthDetailsService(
    private val userJdslRepository: UserJdslRepository,
    private val roleJpaRepository: RoleJpaRepository,
) : UserDetailsService {
	// username is user's id
	override fun loadUserByUsername(username: String): UserDetails {
		val id = UUID.fromString(username)
		val userCredential =
			userJdslRepository.findCredentialById(id).orElseThrow {
				IllegalAccessException()
			}
		val roles = roleJpaRepository.findByUserId(userCredential.id)

		return AuthDetails(userCredential, roles)
	}
}
