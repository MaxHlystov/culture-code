package ru.fmtk.khlystov.culture_code.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*

@Component
@ConfigurationProperties("app")
data class SecurityAppProperties(val auth: Auth = Auth(),
                            val oauth2: OAuth2 = OAuth2()) {

    data class Auth(var tokenSecret: String? = null,
               var tokenExpirationMsec: Long = 0)

    data class OAuth2(private var authorizedRedirectUris: List<String> = ArrayList()) {

        fun authorizedRedirectUris(authorizedRedirectUris: List<String>): OAuth2 {
            this.authorizedRedirectUris = authorizedRedirectUris
            return this
        }
    }
}