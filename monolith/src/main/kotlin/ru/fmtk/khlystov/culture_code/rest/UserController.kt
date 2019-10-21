package ru.fmtk.khlystov.culture_code.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.fmtk.khlystov.culture_code.model.User
import ru.fmtk.khlystov.culture_code.repository.UserRepository
import ru.fmtk.khlystov.culture_code.security.CurrentUser
import ru.fmtk.khlystov.culture_code.security.CustomUserDetails
import ru.fmtk.khlystov.culture_code.security.exception.ResourceNotFoundException
import java.security.InvalidParameterException

@RestController
class UserController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("/users/me")
    @PreAuthorize("hasRole('USER')")
    fun getCurrentUser(@CurrentUser customUserDetails: CustomUserDetails): User {
        val userId = customUserDetails.getId() ?: throw InvalidParameterException("User id is null")
        return userRepository.findById(userId)
                .orElseThrow { ResourceNotFoundException("User", "id", userId) }
    }
}