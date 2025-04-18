package kr.flooding.backend.global.security.configuration

import kr.flooding.backend.domain.role.enums.RoleType
import kr.flooding.backend.global.security.filter.ExceptionFilter
import kr.flooding.backend.global.security.filter.JwtFilter
import kr.flooding.backend.global.security.jwt.JwtProvider
import kr.flooding.backend.global.util.deleteMatchers
import kr.flooding.backend.global.util.getMatchers
import kr.flooding.backend.global.util.patchMatchers
import kr.flooding.backend.global.util.postMatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
	private val jwtProvider: JwtProvider,
) {
	companion object {
		private val ROLE_USER = RoleType.ROLE_USER.name
		private val ROLE_STUDENT = RoleType.ROLE_STUDENT.name
		private val ROLE_TEACHER = RoleType.ROLE_TEACHER.name
	}

	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		val jwtFilter = JwtFilter(jwtProvider)

		return http
			.authorizeHttpRequests {
				it // Neis
					.getMatchers("/neis/**", listOf(ROLE_USER))

				it // User
					.getMatchers("/user/student/search", listOf(ROLE_USER))
					.getMatchers("/user/teacher/search", listOf(ROLE_USER))
					.getMatchers("/user", listOf(ROLE_USER))
					.patchMatchers("/user/profile", listOf(ROLE_USER))
					.deleteMatchers("/user/withdraw", listOf(ROLE_USER))

				it // Homebase
					.getMatchers("/homebase", listOf(ROLE_USER))
					.getMatchers("/homebase/myself", listOf(ROLE_USER))
					.postMatchers("/homebase", listOf(ROLE_USER))
					.patchMatchers("/homebase", listOf(ROLE_USER))
					.deleteMatchers("/homebase/{homebaseGroupId}", listOf(ROLE_USER))

				it // Self Study
					.postMatchers("/self-study", listOf(ROLE_STUDENT))
					.deleteMatchers("/self-study", listOf(ROLE_STUDENT))

				it // Club
					.getMatchers("/club", listOf(ROLE_USER))
					.getMatchers("/club/myself", listOf(ROLE_USER))
					.getMatchers("/club/applicant", listOf(ROLE_USER))
					.getMatchers("/club/{clubId}", listOf(ROLE_USER))
					.postMatchers("/club", listOf(ROLE_USER))
					.postMatchers("/club/applicant", listOf(ROLE_USER))
					.postMatchers("/club/applicant/approve", listOf(ROLE_USER))
					.postMatchers("/club/invite/confirm", listOf(ROLE_USER))
					.postMatchers("/club/{clubId}/member/{userId}", listOf(ROLE_USER))
					.postMatchers("/club/{clubId}/open", listOf(ROLE_USER))
					.postMatchers("/club/{clubId}/close", listOf(ROLE_USER))
					.patchMatchers("/club/{clubId}", listOf(ROLE_USER))
					.deleteMatchers("/club/{clubId}/member/{userId}", listOf(ROLE_USER))
					.deleteMatchers("/club/{clubId}", listOf(ROLE_USER))
					.deleteMatchers("/club/{clubId}/member", listOf(ROLE_USER))

				it // Attendance
					.postMatchers("/attendance/club", listOf(ROLE_USER))
					.deleteMatchers("/attendance/club", listOf(ROLE_USER))
					.postMatchers("/attendance/club/leader", listOf(ROLE_USER))
					.deleteMatchers("/attendance/club/leader", listOf(ROLE_USER))
					.getMatchers("/attendance/myself", listOf(ROLE_USER))

				it // Classroom
					.getMatchers("/classroom", listOf(ROLE_USER))

				it // Music
					.postMatchers("/music", listOf(ROLE_USER))
					.getMatchers("/music", listOf(ROLE_USER))
					.deleteMatchers("/music", listOf(ROLE_USER))
					.patchMatchers("/music/{musicId}/like", listOf(ROLE_USER))

				it // Massage
					.postMatchers("/massage", listOf(ROLE_USER))

				it // File
					.postMatchers("/file/image", listOf(ROLE_USER))

				it // Permit All
					.requestMatchers("/auth/**")
					.permitAll()
					.requestMatchers(HttpMethod.GET, "/actuator/**")
					.permitAll()
					.requestMatchers(HttpMethod.GET, "/swagger-ui/**")
					.permitAll()
					.requestMatchers(HttpMethod.GET, "/api-docs/**")
					.permitAll()
					.anyRequest()
					.denyAll()
			}.csrf { it.disable() }
			.formLogin { it.disable() }
			.httpBasic { it.disable() }
			.sessionManagement {
				it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			}.addFilterBefore(
				ExceptionFilter(),
				UsernamePasswordAuthenticationFilter::class.java,
			).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
			.build()
	}
}
