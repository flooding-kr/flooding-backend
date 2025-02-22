package kr.flooding.backend.global.security.details

import kr.flooding.backend.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails(
	private val user: User,
) : UserDetails {
	override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
		user.roles
			.stream()
			.map {
				SimpleGrantedAuthority(it.name)
			}.toList()
			.toMutableList()

	override fun getPassword(): String = user.encodedPassword

	override fun getUsername(): String = user.email
}
