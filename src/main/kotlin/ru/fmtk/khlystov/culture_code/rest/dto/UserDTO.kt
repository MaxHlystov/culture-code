package ru.fmtk.khlystov.culture_code.rest.dto

import ru.fmtk.khlystov.culture_code.model.User
import ru.fmtk.khlystov.culture_code.security.AuthProvider
import java.util.*

data class UserDTO(var id: String,
                   val name: String,
                   val email: String,
                   val emailVerified: Boolean,
                   val provider: AuthProvider,
                   val providerId: String? = null,
                   val accountNonExpired: Boolean = true,
                   val accountNonLocked: Boolean = true,
                   val credentialsNonExpired: Boolean = true,
                   val enabled: Boolean = true,
                   val roles: Set<String> = HashSet(),
                   val imageUrl: String? = null) {

    constructor(user: User) : this(
            user.id ?: "",
            user.name,
            user.email,
            user.emailVerified,
            user.provider,
            user.providerId,
            user.accountNonExpired,
            user.accountNonLocked,
            user.credentialsNonExpired,
            user.enabled,
            user.roles.asSequence().toHashSet(),
            user.imageUrl)
}