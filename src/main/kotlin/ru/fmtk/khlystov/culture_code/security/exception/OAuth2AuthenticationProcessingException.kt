package ru.fmtk.khlystov.culture_code.security.exception

import org.springframework.security.core.AuthenticationException

class OAuth2AuthenticationProcessingException(message: String)
    : AuthenticationException(message)