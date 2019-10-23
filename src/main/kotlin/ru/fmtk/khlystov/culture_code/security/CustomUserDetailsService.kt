package ru.fmtk.khlystov.culture_code.security

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.fmtk.khlystov.culture_code.repository.UserRepository
import ru.fmtk.khlystov.culture_code.security.exception.ResourceNotFoundException


@Component
class CustomUserDetailsService(val userRepository: UserRepository) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        requireNotNull(username) { "User name is null." }
        val user = userRepository.findByName(username)
                .orElseThrow { UsernameNotFoundException("User Not Found") }
        return CustomUserDetails(user)
    }

    @Transactional
    fun loadUserByEmail(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
                .orElseThrow { BadCredentialsException("User not found with email : $email") }
        return CustomUserDetails(user)
    }

    @Transactional
    fun loadUserById(id: String?): UserDetails {
        requireNotNull(id) { "User id is null." }
        val user = userRepository.findById(id)
                .orElseThrow { ResourceNotFoundException("User", "id", id) }
        return CustomUserDetails(user)
    }
}

