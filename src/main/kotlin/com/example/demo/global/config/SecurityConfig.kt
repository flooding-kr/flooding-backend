package com.example.demo.global.config

import com.example.demo.domain.user.entity.Role
import com.example.demo.global.security.filter.JwtFilter
import com.example.demo.global.security.jwt.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.HttpSecurityBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
class SecurityConfig (
	private val jwtProvider: JwtProvider
) {
	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		val jwtFilter = JwtFilter(jwtProvider)

		return http
			.authorizeHttpRequests { it
				// Auth
				.requestMatchers("/auth/sign-in").permitAll()
				.requestMatchers("/auth/sign-up").permitAll()
				.requestMatchers("/auth/logout").permitAll()
				.requestMatchers("/auth/verify").permitAll()
				.requestMatchers("/auth/re-issue").permitAll()
				.requestMatchers("/auth/re-verify").permitAll()
				.requestMatchers("/auth/withdraw").hasAuthority(Role.ROLE_USER.name)

				// User
				.requestMatchers("/user/**").hasAuthority(Role.ROLE_USER.name)
			}
			.csrf { it.disable() }
			.formLogin { it.disable() }
			.httpBasic { it.disable() }
			.sessionManagement {
				it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			}
			.cors {
				it.configurationSource(corsConfig())
			}
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
			.build()
	}

	fun corsConfig(): CorsConfigurationSource {
		val corsConfigurationSource = CorsConfiguration()
		corsConfigurationSource.addAllowedHeader("*")
		corsConfigurationSource.addAllowedMethod("*")
		corsConfigurationSource.addAllowedOriginPattern("*")
		corsConfigurationSource.allowCredentials = true
		corsConfigurationSource.addExposedHeader("Authorization")

		val urlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()

		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfigurationSource)
		return urlBasedCorsConfigurationSource
	}
}