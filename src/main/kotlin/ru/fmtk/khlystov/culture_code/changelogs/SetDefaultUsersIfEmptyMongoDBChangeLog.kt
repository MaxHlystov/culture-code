package ru.fmtk.khlystov.culture_code.changelogs

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.fmtk.khlystov.culture_code.model.User
import ru.fmtk.khlystov.culture_code.security.Roles
import java.lang.management.BufferPoolMXBean

@ChangeLog(order = "002")
class SetDefaultUsersIfEmptyMongoDBChangeLog {
    private val log: Logger = LoggerFactory.getLogger(SetDefaultUsersIfEmptyMongoDBChangeLog::class.java)

    var passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    @ChangeSet(order = "000", id = "setDefaultUsersIfEmpty", author = "khlystov", runAlways = true)
    fun setDefaultUsersIfEmpty(template: MongoTemplate) {
        fun getRndUser(name: String, roles: Set<Roles> = setOf(Roles.User)): User {
            val password = "111111" //UUID.randomUUID().toString()
            val user = User(null,
                    name,
                    password = passwordEncoder.encode(password),
                    roles = roles.asSequence().map(Roles::toString).toSet())
            log.info("Created admin user with name \"${user.name}\" and password $password")
            return user
        }

        val query = BasicQuery("{}")
        if (!template.exists(query, User::class.java)) {
            template.insert(getRndUser("Admin", setOf(Roles.Admin, Roles.User)))
            template.insert(getRndUser("User"))
        }
    }
}