package ru.fmtk.khlystov.culture_code.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import ru.fmtk.khlystov.culture_code.model.User
import java.util.*

@Repository
interface UserRepository : MongoRepository<User, String> {
    fun findByName(name: String): Optional<User>

    fun findByEmail(email: String): Optional<User>

    fun existsByEmail(email: String): Boolean
}