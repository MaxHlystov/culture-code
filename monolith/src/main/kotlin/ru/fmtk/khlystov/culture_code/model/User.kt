package ru.fmtk.khlystov.culture_code.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import ru.fmtk.khlystov.culture_code.security.AuthProvider
import java.util.*

@Document(collection = "Main_Users")
data class User(@Id var id: String?,
                val name: String,
                @Indexed val email: String = "",
                val emailVerified: Boolean = false,
                val password: String = "",
                val provider: AuthProvider = AuthProvider.GOOGLE,
                val providerId: String? = null,
                val accountNonExpired: Boolean = true,
                val accountNonLocked: Boolean = true,
                val credentialsNonExpired: Boolean = true,
                val enabled: Boolean = true,
                val roles: Set<String> = HashSet(),
                val imageUrl: String? = null) {

    constructor(name: String) : this(null, name)
    constructor(user: User) : this(
            user.id,
            user.name,
            user.email,
            user.emailVerified,
            user.password,
            user.provider,
            user.providerId,
            user.accountNonExpired,
            user.accountNonLocked,
            user.credentialsNonExpired,
            user.enabled,
            user.roles.asSequence().toHashSet(),
            user.imageUrl)


    fun newWithRoles(roles: Collection<String>) = User(
            id,
            name,
            email,
            emailVerified,
            password,
            provider,
            providerId,
            accountNonExpired,
            accountNonLocked,
            credentialsNonExpired,
            enabled,
            this.roles + roles,
            imageUrl)

    override fun toString(): String {
        return "User(id=$id, name='$name', email='$email', provider='$provider', password='$password')"
    }
}