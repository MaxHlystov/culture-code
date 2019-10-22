package ru.fmtk.khlystov.culture_code.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*

@ConfigurationProperties("app")
class SecurityAppProperties {

    var auth: Auth = Auth()
    var oauth2: OAuth2 = OAuth2()

    data class Auth(var tokenSecret: String? = null,
               var tokenExpirationMsec: Long = 0)

    data class OAuth2(var authorizedRedirectUris: List<String> = ArrayList()) {

        fun authorizedRedirectUris(authorizedRedirectUris: List<String>): OAuth2 {
            this.authorizedRedirectUris = authorizedRedirectUris
            return this
        }
    }
}