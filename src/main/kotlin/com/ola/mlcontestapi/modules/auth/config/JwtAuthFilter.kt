package com.ola.mlcontestapi.modules.auth.config

import com.ola.mlcontestapi.modules.auth.services.AppUserDetailsService
import com.ola.mlcontestapi.modules.auth.services.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val appUserDetailsService: AppUserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        var token: String? = null
        var username: String? = null

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7)
            username = jwtService.extractUsername(token)
        }

        if (username != null && token != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = appUserDetailsService.loadUserByUsername(username)

            if (jwtService.validateToken(token, userDetails)) {
                val authenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )

                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return path.startsWith("/auth")
                || path.startsWith("/datasets")
                || path.startsWith("/images")
                || path.startsWith("/categories")
                || path.startsWith("/tags")
    }
}