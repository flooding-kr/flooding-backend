package team.gsm.flooding.global.config

import team.gsm.flooding.domain.user.entity.Role
import team.gsm.flooding.global.security.filter.JwtFilter
import team.gsm.flooding.global.security.jwt.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import team.gsm.flooding.global.security.filter.ExceptionFilter


@Configuration
class SecurityConfig (
	private val jwtProvider: JwtProvider
) {
	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		val jwtFilter = JwtFilter(jwtProvider)

		return http
			.authorizeHttpRequests { it
				// 인증
				.requestMatchers(HttpMethod.POST, "/auth/sign-up").permitAll()
				.requestMatchers(HttpMethod.POST, "/auth/sign-in").permitAll()
				.requestMatchers(HttpMethod.POST, "/auth/logout").permitAll()
				.requestMatchers(HttpMethod.GET, "/auth/verify").permitAll()
				.requestMatchers(HttpMethod.PATCH, "/auth/re-issue").permitAll()
				.requestMatchers(HttpMethod.PATCH, "/auth/re-verify").permitAll()

				// 사용자
				.requestMatchers(HttpMethod.GET, "/user").hasAuthority(Role.ROLE_USER.name)
				.requestMatchers(HttpMethod.GET, "/user/search").hasAuthority(Role.ROLE_USER.name)
				.requestMatchers(HttpMethod.DELETE, "/user/withdraw").hasAuthority(Role.ROLE_USER.name)

				// 급식
				.requestMatchers(HttpMethod.GET, "/lunch").hasAuthority(Role.ROLE_USER.name)

				// 홈베이스
				.requestMatchers(HttpMethod.POST, "/attendance/homebase").hasAuthority(Role.ROLE_USER.name)
				.requestMatchers(HttpMethod.PATCH, "/attendance/homebase").hasAuthority(Role.ROLE_USER.name)
				.requestMatchers(HttpMethod.DELETE, "/attendance/homebase/{homebaseGroupId}").hasAuthority(Role.ROLE_USER.name)
				.requestMatchers(HttpMethod.GET, "/attendance/homebase").hasAnyAuthority(Role.ROLE_USER.name)
				.requestMatchers(HttpMethod.GET, "/attendance/homebase/myself").hasAnyAuthority(Role.ROLE_USER.name)

				// 서버 상태
				.requestMatchers(HttpMethod.GET, "/health").permitAll()
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
			.addFilterBefore(ExceptionFilter(), UsernamePasswordAuthenticationFilter::class.java)
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