package ru.fmtk.khlystov.culture_code.security

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationFilter : OncePerRequestFilter() {
    companion object {
        private val logger = LoggerFactory.getLogger(TokenAuthenticationFilter::class.java)
        private const val TOKEN_AUTHENTICATION_FILTER__TOKEN_PREFIX = "Bearer "
    }

    @Autowired
    private lateinit var tokenProvider: TokenProvider

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain) {
        try {
            val jwt = getJwtFromRequest(request)

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                val userId = tokenProvider.getUserIdFromToken(jwt) ?: ""

                val userDetails = customUserDetailsService.loadUserById(userId)
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (ex: Exception) {
            logger.error("Could not set user authentication in security context", ex)
        }
        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith(TOKEN_AUTHENTICATION_FILTER__TOKEN_PREFIX)) {
            bearerToken.removePrefix(TOKEN_AUTHENTICATION_FILTER__TOKEN_PREFIX)
        } else null
    }
}