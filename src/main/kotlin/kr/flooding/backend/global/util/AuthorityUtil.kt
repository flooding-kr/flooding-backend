package kr.flooding.backend.global.util

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer

fun AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry.getMatchers(
	pattern: String,
	authorities: List<String>,
): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry =
	authorityMatchers(HttpMethod.GET, pattern, authorities)

fun AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry.postMatchers(
	pattern: String,
	authorities: List<String>,
): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry =
	authorityMatchers(HttpMethod.POST, pattern, authorities)

fun AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry.patchMatchers(
	pattern: String,
	authorities: List<String>,
): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry =
	authorityMatchers(HttpMethod.PATCH, pattern, authorities)

fun AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry.deleteMatchers(
	pattern: String,
	authorities: List<String>,
): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry =
	authorityMatchers(HttpMethod.DELETE, pattern, authorities)

fun AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry.putMatchers(
	pattern: String,
	authorities: List<String>,
): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry =
	authorityMatchers(HttpMethod.PUT, pattern, authorities)

fun AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry.authorityMatchers(
	httpMethod: HttpMethod,
	pattern: String,
	authorities: List<String>,
): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry =
	this.requestMatchers(httpMethod, pattern).hasAnyAuthority(*authorities.toTypedArray())
