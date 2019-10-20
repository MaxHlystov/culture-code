package ru.fmtk.khlystov.culture_code.security

import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenProvider(private val securityAppProperties: SecurityAppProperties) {
    private val logger = LoggerFactory.getLogger(TokenProvider::class.java)

    fun createToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as CustomUserDetails

        val now = Date()
        val expiryDate = Date(now.time + securityAppProperties.auth.tokenExpirationMsec)

        return Jwts.builder()
                .setSubject(userPrincipal.getId().toString())
                .setIssuedAt(Date())
                .setExpiration(expiryDate)
                .signWith(
                        SignatureAlgorithm.HS512,
                        securityAppProperties.auth.tokenSecret
                )
                .compact()
    }

    fun getUserIdFromToken(token: String?): String? {
        val claims = Jwts.parser()
                .setSigningKey(securityAppProperties.auth.tokenSecret)
                .parseClaimsJws(token)
                .body
        return claims.subject
    }

    fun validateToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(securityAppProperties.auth.tokenSecret).parseClaimsJws(authToken)
            return true
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty.")
        }
        return false
    }
}