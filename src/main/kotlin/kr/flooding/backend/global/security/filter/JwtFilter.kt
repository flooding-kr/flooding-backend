package kr.flooding.backend.global.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.flooding.backend.global.security.jwt.JwtProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
	private val jwtProvider: JwtProvider,
) : OncePerRequestFilter() {
	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain,
	) {
		val token = request.getHeader("Authorization")

		if (token != null) {
			val authentication = jwtProvider.getAuthentication(token)
			SecurityContextHolder.getContext().authentication = authentication
		}

		filterChain.doFilter(request, response)
	}
}
