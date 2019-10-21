package ru.fmtk.khlystov.culture_code.security.oauth2

import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import ru.fmtk.khlystov.culture_code.model.User
import ru.fmtk.khlystov.culture_code.repository.UserRepository
import ru.fmtk.khlystov.culture_code.security.AuthProvider
import ru.fmtk.khlystov.culture_code.security.CustomUserDetails
import ru.fmtk.khlystov.culture_code.security.exception.OAuth2AuthenticationProcessingException
import ru.fmtk.khlystov.culture_code.security.oauth2.user.OAuth2UserInfo
import ru.fmtk.khlystov.culture_code.security.oauth2.user.getOAuth2UserInfo

@Service
class CustomOAuth2UserService(private val userRepository: UserRepository) : DefaultOAuth2UserService() {

    override fun loadUser(oAuth2UserRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(oAuth2UserRequest)
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User)
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw InternalAuthenticationServiceException(ex.message, ex.cause)
        }
    }

    private fun processOAuth2User(oAuth2UserRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {
        val oAuth2UserInfo = getOAuth2UserInfo(
                oAuth2UserRequest.clientRegistration.registrationId, oAuth2User.attributes)
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider")
        }
        val userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail())
        var user: User = userOptional.map { user ->
            if (user.provider != AuthProvider.valueOf(oAuth2UserRequest.clientRegistration.registrationId.toUpperCase())) {
                throw OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        oAuth2UserRequest.clientRegistration.registrationId + " account. Please use your " +
                        user.provider.toString() + " account to login.")
            }
            updateExistingUser(user, oAuth2UserInfo)
        }.orElseGet { registerNewUser(oAuth2UserRequest, oAuth2UserInfo) }

        return CustomUserDetails(user, oAuth2User.attributes)
    }

    private fun registerNewUser(oAuth2UserRequest: OAuth2UserRequest, oAuth2UserInfo: OAuth2UserInfo): User {
        val user = User(
                null,
                name = oAuth2UserInfo.getName(),
                email = oAuth2UserInfo.getEmail(),
                provider = AuthProvider.valueOf(oAuth2UserRequest.clientRegistration.registrationId.toUpperCase()),
                providerId = oAuth2UserInfo.getId(),
                imageUrl = oAuth2UserInfo.getImageUrl())
        return userRepository.save(user)
    }

    private fun updateExistingUser(existingUser: User, oAuth2UserInfo: OAuth2UserInfo): User {
        val newUser = User(existingUser.id,
                oAuth2UserInfo.getName(),
                existingUser.email,
                existingUser.emailVerified,
                existingUser.password,
                existingUser.provider,
                existingUser.providerId,
                existingUser.accountNonExpired,
                existingUser.accountNonLocked,
                existingUser.credentialsNonExpired,
                existingUser.enabled,
                existingUser.roles.asSequence().toHashSet(),
                oAuth2UserInfo.getImageUrl())
        return userRepository.save(newUser)
    }
}