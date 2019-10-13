package ru.fmtk.khlystov.culture_code.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import ru.fmtk.khlystov.culture_code.repository.UserRepository


@Component
class CustomUserDetailsService(val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if(username == null) { throw IllegalArgumentException("User name is null.") }
        val user = userRepository.findByName(username) ?: throw UsernameNotFoundException("User Not Found")
        return CustomUserDetails(user)
    }
}

