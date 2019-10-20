package ru.fmtk.khlystov.culture_code.security.oauth2.user

import ru.fmtk.khlystov.culture_code.security.AuthProvider
import ru.fmtk.khlystov.culture_code.security.exception.OAuth2AuthenticationProcessingException

fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
    return if (registrationId.equals(AuthProvider.GOOGLE.toString(), ignoreCase = true)) {
        GoogleOAuth2UserInfo(attributes)
    } else {
        throw OAuth2AuthenticationProcessingException("Login with $registrationId is not supported yet.")
    }
}
