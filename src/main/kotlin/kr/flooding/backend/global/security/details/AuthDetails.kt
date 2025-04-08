package kr.flooding.backend.global.security.details

import kr.flooding.backend.domain.role.entity.Role
import kr.flooding.backend.global.security.model.UserCredential
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails(
	private val credential: UserCredential,
	private val roles: List<Role>,
) : UserDetails {
	override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
		roles
			.stream()
			.map {
				SimpleGrantedAuthority(it.type.name)
			}.toList()
			.toMutableList()

	override fun getPassword(): String = credential.encodedPassword

	override fun getUsername(): String = credential.email
}
