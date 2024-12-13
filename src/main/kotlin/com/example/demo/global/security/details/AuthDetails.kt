package com.example.demo.global.security.details

import com.example.demo.domain.user.entity.User
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails(
	private val user: User
): UserDetails {
	override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
		return user.roles.stream().map {
			SimpleGrantedAuthority(it.name)
		}.toList().toMutableList()
	}

	override fun getPassword(): String = user.encodedPassword

	override fun getUsername(): String = user.email
}