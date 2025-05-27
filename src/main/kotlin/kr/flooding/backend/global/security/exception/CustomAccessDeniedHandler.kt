package kr.flooding.backend.global.security.exception

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.flooding.backend.global.cache.PathPatternCache
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.http.server.PathContainer
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler(
    private val pathPatternCache: PathPatternCache,
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        if (isUnknownPath(request!!)) {
            throw HttpException(ExceptionEnum.AUTH.NOT_FOUND_PATH.toPair())
        }

        throw HttpException(ExceptionEnum.AUTH.FORBIDDEN.toPair())
    }

    private fun isUnknownPath(request: HttpServletRequest): Boolean {
        val requestUrl = request.requestURI.removePrefix(request.contextPath)
        val requestPath = PathContainer.parsePath(requestUrl)
        val requestMethod = request.method

        return pathPatternCache.pathPatterns.none {
            val pathPattern = it.first
            val methods = it.second

            pathPattern.matches(requestPath) && requestMethod in methods
        }
    }
}