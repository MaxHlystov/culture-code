package ru.fmtk.khlystov.culture_code.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import ru.fmtk.khlystov.culture_code.model.User

open class CustomUserDetails(val user: User,
                             private var attributes: Map<String, Any> = HashMap()
) : OAuth2User, UserDetails {

    fun getId(): String? {
        return user.id
    }

    override fun getName(): String {
        return user.id.toString()
    }

    fun getEmail(): String {
        return user.email
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return user.roles.asSequence().map(::Authority).toList()
    }

    override fun getUsername(): String {
        return user.name
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun isEnabled(): Boolean {
        return user.enabled
    }

    override fun isCredentialsNonExpired(): Boolean {
        return user.credentialsNonExpired
    }

    override fun isAccountNonExpired(): Boolean {
        return user.accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return user.accountNonLocked
    }

    override fun getAttributes(): Map<String, Any> {
        return attributes
    }

    fun setAttributes(attributes: Map<String, Any>) {
        this.attributes = attributes
    }

}