package team.gsm.flooding.global.config

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
import team.gsm.flooding.domain.user.entity.Role
import team.gsm.flooding.global.security.filter.ExceptionFilter
import team.gsm.flooding.global.security.filter.JwtFilter
import team.gsm.flooding.global.security.jwt.JwtProvider

@Configuration
class SecurityConfig(
	private val jwtProvider: JwtProvider,
) {
	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		val jwtFilter = JwtFilter(jwtProvider)

		return http
			.authorizeHttpRequests {
				it
					// 인증
					.requestMatchers(HttpMethod.POST, "/auth/sign-up")
					.permitAll()
					.requestMatchers(HttpMethod.POST, "/auth/sign-in")
					.permitAll()
					.requestMatchers(HttpMethod.POST, "/auth/logout")
					.permitAll()
					.requestMatchers(HttpMethod.GET, "/auth/verify")
					.permitAll()
					.requestMatchers(HttpMethod.PATCH, "/auth/re-issue")
					.permitAll()
					.requestMatchers(HttpMethod.PATCH, "/auth/re-verify")
					.permitAll()
					// 사용자
					.requestMatchers(HttpMethod.GET, "/user")
					.hasAuthority(Role.ROLE_USER.name)
					.requestMatchers(HttpMethod.GET, "/user/search")
					.hasAuthority(Role.ROLE_USER.name)
					.requestMatchers(HttpMethod.DELETE, "/user/withdraw")
					.hasAuthority(Role.ROLE_USER.name)
					// 나이스 API
					.requestMatchers(HttpMethod.GET, "/neis/**")
					.hasAuthority(Role.ROLE_USER.name)
					// 홈베이스
					.requestMatchers(HttpMethod.POST, "/homebase")
					.hasAuthority(Role.ROLE_USER.name)
					.requestMatchers(HttpMethod.PATCH, "/homebase")
					.hasAuthority(Role.ROLE_USER.name)
					.requestMatchers(HttpMethod.DELETE, "/homebase/{homebaseGroupId}")
					.hasAuthority(Role.ROLE_USER.name)
					.requestMatchers(HttpMethod.GET, "/homebase")
					.hasAnyAuthority(Role.ROLE_USER.name)
					.requestMatchers(HttpMethod.GET, "/homebase/myself")
					.hasAnyAuthority(Role.ROLE_USER.name)
					// 동아리
					.requestMatchers(HttpMethod.POST, "/club")
					.hasAnyAuthority(Role.ROLE_USER.name)
					.requestMatchers(HttpMethod.PATCH, "/club/{clubId}")
					.hasAnyAuthority(Role.ROLE_USER.name)
					.requestMatchers(HttpMethod.GET, "/club")
					.hasAnyAuthority(Role.ROLE_USER.name)
					.requestMatchers(HttpMethod.DELETE, "/club/{clubId}/member/{userId}")
					.hasAnyAuthority(Role.ROLE_USER.name)
					.requestMatchers(HttpMethod.POST, "/club/applicant")
					.hasAnyAuthority(Role.ROLE_USER.name)
					// 파일
					.requestMatchers(HttpMethod.POST, "/file/image")
					.hasAnyAuthority(Role.ROLE_USER.name)
					// 상태 확인
					.requestMatchers(HttpMethod.GET, "/actuator/**")
					.permitAll()
					// API 명세서
					.requestMatchers(HttpMethod.GET, "/swagger-ui/**")
					.permitAll()
					.requestMatchers(HttpMethod.GET, "/api-docs/**")
					.permitAll()
			}.csrf { it.disable() }
			.formLogin { it.disable() }
			.httpBasic { it.disable() }
			.sessionManagement {
				it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			}.cors {
				it.configurationSource(corsConfig())
			}.addFilterBefore(ExceptionFilter(), UsernamePasswordAuthenticationFilter::class.java)
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
