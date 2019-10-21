package ru.fmtk.khlystov.culture_code.security.oauth2

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import ru.fmtk.khlystov.culture_code.security.SecurityAppProperties
import ru.fmtk.khlystov.culture_code.security.TokenProvider
import ru.fmtk.khlystov.culture_code.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.Companion.REDIRECT_URI_PARAM_COOKIE_NAME
import ru.fmtk.khlystov.culture_code.security.utils.getCookie
import java.net.URI
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationSuccessHandler(
        val tokenProvider: TokenProvider,
        val appProperties: SecurityAppProperties,
        val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest,
                                         response: HttpServletResponse,
                                         authentication: Authentication) {
        val targetUrl = determineTargetUrl(request, response, authentication)

        if (response.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }

        clearAuthenticationAttributes(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    protected fun determineTargetUrl(request: HttpServletRequest,
                                     response: HttpServletResponse,
                                     authentication: Authentication): String {
        val targetUrl = getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .map { redirectUri ->
                    if (!isAuthorizedRedirectUri(redirectUri)) {
                        throw BadCredentialsException(
                                "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
                    }
                    redirectUri
                }
                .orElseGet { defaultTargetUrl }
        val token = tokenProvider.createToken(authentication)
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build()
                .toUriString()
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
    }

    private fun isAuthorizedRedirectUri(uri: String): Boolean {
        val clientRedirectUri = URI.create(uri)
        return appProperties.oauth2.authorizedRedirectUris
                .asSequence()
                .any { authorizedRedirectUri ->
                    // Only validate host and port. Let the clients use different paths if they want to
                    val authorizedURI = URI.create(authorizedRedirectUri)
                    authorizedURI.host.equals(clientRedirectUri.host, ignoreCase = true)
                            && authorizedURI.port == clientRedirectUri.port
                }
    }
}