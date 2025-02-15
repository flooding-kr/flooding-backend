package team.gsm.flooding.global.security.details

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import team.gsm.flooding.domain.user.entity.User

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
