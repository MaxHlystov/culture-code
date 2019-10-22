package ru.fmtk.khlystov.culture_code.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.fmtk.khlystov.culture_code.repository.UserRepository
import ru.fmtk.khlystov.culture_code.rest.dto.UserDTO
import ru.fmtk.khlystov.culture_code.security.CurrentUser
import ru.fmtk.khlystov.culture_code.security.CustomUserDetails
import ru.fmtk.khlystov.culture_code.security.exception.ResourceNotFoundException
import java.security.InvalidParameterException

@RestController
class UserController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("/users/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    fun getCurrentUser(@CurrentUser customUserDetails: CustomUserDetails): UserDTO {
        val userId = customUserDetails.getId() ?: throw InvalidParameterException("User id is null")
        return userRepository.findById(userId)
                .map(::UserDTO)
                .orElseThrow { ResourceNotFoundException("User", "id", userId) }
    }
}