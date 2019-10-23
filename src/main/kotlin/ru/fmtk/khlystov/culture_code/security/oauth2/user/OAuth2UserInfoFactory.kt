package ru.fmtk.khlystov.culture_code.security.oauth2.user

import ru.fmtk.khlystov.culture_code.security.AuthProvider
import ru.fmtk.khlystov.culture_code.security.exception.OAuth2AuthenticationProcessingException

fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
    return when {
        registrationId.equals(AuthProvider.GOOGLE.toString(), ignoreCase = true) -> GoogleOAuth2UserInfo(attributes)

        registrationId.equals(AuthProvider.GITHUB.toString(), ignoreCase = true) -> run {
            if (!attributes.containsKey("email")) {
                throw OAuth2AuthenticationProcessingException("Github login without public email is not supported.")
            }
            return GithubOAuth2UserInfo(attributes)
        }

        else -> throw OAuth2AuthenticationProcessingException("Login with $registrationId is not supported yet.")
    }
}
