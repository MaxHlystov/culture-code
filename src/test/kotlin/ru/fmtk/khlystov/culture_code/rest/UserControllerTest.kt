package ru.fmtk.khlystov.culture_code.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.fmtk.khlystov.culture_code.model.User
import ru.fmtk.khlystov.culture_code.repository.UserRepository
import ru.fmtk.khlystov.culture_code.rest.dto.UserDTO
import ru.fmtk.khlystov.culture_code.security.*
import ru.fmtk.khlystov.culture_code.security.oauth2.CustomOAuth2UserService
import ru.fmtk.khlystov.culture_code.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository
import ru.fmtk.khlystov.culture_code.security.oauth2.OAuth2AuthenticationFailureHandler
import ru.fmtk.khlystov.culture_code.security.oauth2.OAuth2AuthenticationSuccessHandler
import java.util.*

@AutoConfigureMockMvc(secure = false)
@WebMvcTest(UserController::class)
@DisplayName("UserController must ")
internal class UserControllerTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var trustedUser: User

    @Autowired
    lateinit var adminUser: User

    @Autowired
    lateinit var mvc: MockMvc

    companion object {
        const val testId = "123456789"
        const val adminId = "555555555"
        const val notTrustedUserId = "notTrustedUserId"
        const val adminUserName = "Admin name"
        const val trustedUserName = "StoredInDB"
        const val notTrustedUserName = "Not trusted user name"
        const val notTrustedUserEmail = "Not trusted user email"
        const val emailTemplate = "@email.localhost"
        const val password = "111111"
        val jsonMapper = jacksonObjectMapper()
    }

    @Test
    @WithUserDetails(adminUserName)
    @DisplayName("return DTO for current user")
    fun gettingCurrentUser() {
        val jsonMatch = jsonMapper.writeValueAsString(UserDTO(adminUser))
        mvc.perform(MockMvcRequestBuilders.get("/users/me"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(jsonMatch))
    }

    @Configuration
    class TestConfig {

        private lateinit var trustedUser: User
        private lateinit var adminUser: User
        private lateinit var encodedPassword: String
        private lateinit var validUsers: List<User>

        init {
            val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
            encodedPassword = passwordEncoder.encode(password)
            trustedUser = User(testId, trustedUserName, trustedUserName + emailTemplate,
                    provider = AuthProvider.LOCAL,
                    password = encodedPassword,
                    roles = listOf<String>(Roles.USER.role).toSet())
            adminUser = User(adminId, adminUserName, adminUserName + emailTemplate,
                    provider = AuthProvider.LOCAL,
                    password = encodedPassword,
                    roles = listOf<String>(Roles.ADMIN.role).toSet())
            validUsers = listOf(adminUser, trustedUser)
        }

        @Bean(name = ["userRepository"])
        fun getUserRepository(): UserRepository {
            val userRepository: UserRepository = Mockito.mock(UserRepository::class.java)
            given(userRepository.findAll())
                    .willReturn(validUsers)
            validUsers.forEach { user ->
                given(userRepository.findByName(user.name))
                        .willReturn(Optional.of(user))
                given(userRepository.findById(user.id ?: ""))
                        .willReturn(Optional.of(user))
                given(userRepository.findByEmail(user.email))
                        .willReturn(Optional.of(user))
                given(userRepository.existsByEmail(user.email))
                        .willReturn(true)
            }
            given(userRepository.findByName(notTrustedUserName))
                    .willReturn(Optional.empty())
            given(userRepository.existsByEmail(notTrustedUserEmail))
                    .willReturn(false)
            return userRepository
        }

        @Bean(name = ["trustedUser"])
        fun getTrustedUser(): User = trustedUser

        @Bean(name = ["adminUser"])
        fun getAdminUser(): User = adminUser

        @Bean(name = ["validUsers"])
        @Qualifier("validUsers")
        fun getValidUsers(): List<User> = validUsers
    }
}