package kr.flooding.backend.global.security.configuration

import kr.flooding.backend.domain.role.enums.RoleType
import kr.flooding.backend.global.security.exception.CustomAccessDeniedHandler
import kr.flooding.backend.global.security.filter.ExceptionFilter
import kr.flooding.backend.global.security.filter.JwtFilter
import kr.flooding.backend.global.security.jwt.JwtProvider
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
        private val ROLE_USER_ADMIN = RoleType.ROLE_USER_ADMIN.name
        private val ROLE_DORMITORY_COUNCIL = RoleType.ROLE_DORMITORY_COUNCIL.name
        private val ROLE_DORMITORY_TEACHER = RoleType.ROLE_DORMITORY_TEACHER.name
		private val ROLE_CLUB_ADMIN = RoleType.ROLE_CLUB_ADMIN.name
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        customAccessDeniedHandler: CustomAccessDeniedHandler
    ): SecurityFilterChain {
        val jwtFilter = JwtFilter(jwtProvider)

		return http.authorizeHttpRequests {
			it // Neis
				.requestMatchers("/neis/**").hasAuthority(ROLE_USER)

			it // User
				.requestMatchers(HttpMethod.GET, "/user/student/search").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.GET, "/user/teacher/search").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.GET, "/user/myself").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.PATCH, "/user/myself").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.DELETE, "/user/withdraw").hasAuthority(ROLE_USER)

			it // User Management
				.requestMatchers(HttpMethod.GET, "/admin/user/pending").hasAuthority(ROLE_USER_ADMIN)
				.requestMatchers(HttpMethod.PATCH, "/admin/user/{userId}/approve").hasAuthority(ROLE_USER_ADMIN)

			it // Homebase
				.requestMatchers(HttpMethod.GET, "/homebase").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.GET, "/homebase/myself").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.POST, "/homebase").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.PATCH, "/homebase").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.DELETE, "/homebase/{homebaseGroupId}").hasAuthority(ROLE_USER)

			it // Self Study
				.requestMatchers(HttpMethod.POST, "/self-study").hasAuthority(ROLE_STUDENT)
				.requestMatchers(HttpMethod.DELETE, "/self-study").hasAuthority(ROLE_STUDENT)
				.requestMatchers(HttpMethod.GET, "/self-study/status").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.GET, "/self-study/rank").hasAuthority(ROLE_USER)

			it // Self Study Management
				.requestMatchers(HttpMethod.PATCH, "/admin/self-study/limit").hasAnyAuthority(ROLE_DORMITORY_COUNCIL, ROLE_DORMITORY_TEACHER)
				.requestMatchers(HttpMethod.GET, "/admin/self-study/ban").hasAnyAuthority(ROLE_DORMITORY_COUNCIL, ROLE_DORMITORY_TEACHER)
				.requestMatchers(HttpMethod.POST, "/admin/self-study/{selfStudyReservationId}/ban").hasAnyAuthority(ROLE_DORMITORY_COUNCIL, ROLE_DORMITORY_TEACHER)
				.requestMatchers(HttpMethod.DELETE, "/admin/self-study/{selfStudyBanId}/ban").hasAnyAuthority(ROLE_DORMITORY_COUNCIL, ROLE_DORMITORY_TEACHER)
				.requestMatchers(HttpMethod.PATCH, "/admin/self-study/{selfStudyReservationId}/attend").hasAnyAuthority(ROLE_DORMITORY_COUNCIL, ROLE_DORMITORY_TEACHER)
				.requestMatchers(HttpMethod.PATCH, "/admin/self-study/{selfStudyReservationId}/absence").hasAnyAuthority(ROLE_DORMITORY_COUNCIL, ROLE_DORMITORY_TEACHER)

			it // Club
				.requestMatchers(HttpMethod.GET, "/club").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.GET, "/club/myself").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.GET, "/club/applicant").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.GET, "/club/{clubId}").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.POST, "/club").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.POST, "/club/applicant").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.POST, "/club/applicant/approve").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.POST, "/club/invite/confirm").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.POST, "/club/{clubId}/member/{userId}").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.POST, "/club/{clubId}/open").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.POST, "/club/{clubId}/close").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.PATCH, "/club/{clubId}").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.DELETE, "/club/applicant/reject").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.DELETE, "/club/{clubId}/member/{userId}").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.DELETE, "/club/{clubId}").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.DELETE, "/club/{clubId}/member").hasAuthority(ROLE_USER)

			it // Club Management
				.requestMatchers(HttpMethod.POST, "/admin/club/approve").hasAuthority(ROLE_CLUB_ADMIN)

			it // Attendance
				.requestMatchers(HttpMethod.POST, "/attendance/club").hasAuthority(ROLE_STUDENT)
				.requestMatchers(HttpMethod.DELETE, "/attendance/club").hasAuthority(ROLE_STUDENT)
				.requestMatchers(HttpMethod.POST, "/attendance/club/manager").hasAnyAuthority(ROLE_STUDENT, ROLE_TEACHER)
				.requestMatchers(HttpMethod.DELETE, "/attendance/club/manager").hasAnyAuthority(ROLE_STUDENT, ROLE_TEACHER)
				.requestMatchers(HttpMethod.GET, "/attendance/myself").hasAnyAuthority(ROLE_STUDENT)
				.requestMatchers(HttpMethod.GET, "/attendance/club/{clubId}").hasAnyAuthority(ROLE_STUDENT, ROLE_TEACHER)

			it // Classroom
				.requestMatchers(HttpMethod.GET, "/classroom").hasAuthority(ROLE_USER)

			it // Music
				.requestMatchers(HttpMethod.POST, "/music").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.GET, "/music").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.DELETE, "/music").hasAuthority(ROLE_USER)
				.requestMatchers(HttpMethod.PATCH, "/music/{musicId}/like").hasAuthority(ROLE_USER)

			it // Music Management
				.requestMatchers(HttpMethod.DELETE, "/admin/music/{musicId}").hasAnyAuthority(ROLE_DORMITORY_COUNCIL, ROLE_DORMITORY_TEACHER)

			it // Massage
				.requestMatchers(HttpMethod.POST, "/massage").hasAuthority(ROLE_STUDENT)
				.requestMatchers(HttpMethod.DELETE, "/massage").hasAuthority(ROLE_STUDENT)
        		.requestMatchers(HttpMethod.GET, "/massage/status").hasAuthority(ROLE_USER)
        		.requestMatchers(HttpMethod.GET, "/massage/rank").hasAuthority(ROLE_USER)

			it // Massage Management
				.requestMatchers(HttpMethod.PATCH, "/admin/massage/limit").hasAnyAuthority(ROLE_DORMITORY_COUNCIL, ROLE_DORMITORY_TEACHER)

			it // Notice
				.requestMatchers(HttpMethod.GET, "/notice").hasAnyAuthority(ROLE_USER)

			it // Notice Management
				.requestMatchers(HttpMethod.POST, "/admin/notice").hasAnyAuthority(ROLE_TEACHER, ROLE_USER_ADMIN, ROLE_DORMITORY_COUNCIL, ROLE_DORMITORY_TEACHER)
				.requestMatchers(HttpMethod.DELETE, "/admin/notice/{noticeId}").hasAnyAuthority(ROLE_TEACHER, ROLE_USER_ADMIN, ROLE_DORMITORY_COUNCIL, ROLE_DORMITORY_TEACHER)

			it // File
				.requestMatchers(HttpMethod.POST, "/file/image").hasAuthority(ROLE_USER)

			it // Permit All
				.requestMatchers("/auth/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api-docs/**").permitAll()
				.anyRequest().denyAll()
		}.csrf {
			it.disable()
		}.formLogin {
			it.disable()
		}.httpBasic {
			it.disable()
		}.sessionManagement {
			it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		}.exceptionHandling {
            it.accessDeniedHandler(customAccessDeniedHandler)
        }.addFilterBefore(
			ExceptionFilter(),
			UsernamePasswordAuthenticationFilter::class.java,
		).addFilterBefore(
			jwtFilter,
			UsernamePasswordAuthenticationFilter::class.java
		).build()
	}
}
