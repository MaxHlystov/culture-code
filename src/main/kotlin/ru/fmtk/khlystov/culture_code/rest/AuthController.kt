package ru.fmtk.khlystov.culture_code.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import ru.fmtk.khlystov.culture_code.model.User
import ru.fmtk.khlystov.culture_code.repository.UserRepository
import ru.fmtk.khlystov.culture_code.rest.dto.ApiResponse
import ru.fmtk.khlystov.culture_code.rest.dto.AuthResponse
import ru.fmtk.khlystov.culture_code.rest.dto.LoginRequest
import ru.fmtk.khlystov.culture_code.rest.dto.SignUpRequest
import ru.fmtk.khlystov.culture_code.security.AuthProvider
import ru.fmtk.khlystov.culture_code.security.Roles
import ru.fmtk.khlystov.culture_code.security.TokenProvider
import ru.fmtk.khlystov.culture_code.security.exception.BadRequestException
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var tokenProvider: TokenProvider

    @PostMapping("/login")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.name, loginRequest.password))
        SecurityContextHolder.getContext().authentication = authentication
        val token = tokenProvider.createToken(authentication)
        return ResponseEntity.ok(AuthResponse(token))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<*> {
        if (userRepository.existsByEmail(signUpRequest.email)) {
            throw BadRequestException("Email address already in use.")
        }
        val user = User(null,
                signUpRequest.name,
                signUpRequest.email,
                password = passwordEncoder.encode(signUpRequest.password),
                provider = AuthProvider.LOCAL,
                roles = setOf(Roles.USER.role))
        val result = userRepository.save(user)
        val location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.id).toUri()
        return ResponseEntity.created(location)
                .body<Any>(ApiResponse(true, "User registered successfully@"))
    }
}