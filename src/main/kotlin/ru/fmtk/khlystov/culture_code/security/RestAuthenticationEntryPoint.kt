package ru.fmtk.khlystov.culture_code.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
    private val logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint::class.java)

    override fun commence(httpServletRequest: HttpServletRequest,
                          httpServletResponse: HttpServletResponse,
                          exception: AuthenticationException) {
        logger.error("Responding with unauthorized error. Message - {}", exception.message)
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                exception.localizedMessage)
    }
}