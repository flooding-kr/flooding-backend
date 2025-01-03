package team.gsm.flooding.global.security.filter

import team.gsm.flooding.global.security.jwt.JwtProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
	private val jwtProvider: JwtProvider
): OncePerRequestFilter() {
	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain
	) {
		val token = resolveToken(request.getHeader("Authorization"))

		if(token != null){
			val authentication = jwtProvider.getAuthentication(token)
			SecurityContextHolder.getContext().authentication = authentication
		}

		filterChain.doFilter(request, response)
	}

	private fun resolveToken(token: String?): String? {
		return if(token == null){
			null
		} else if(token.startsWith("Bearer ")) {
			token.substring(7)
		} else null
	}
}