package ru.fmtk.khlystov.culture_code.sevice.implementation

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.repository.UserRepository
import ru.fmtk.khlystov.culture_code.rest.dto.UserDTO
import ru.fmtk.khlystov.culture_code.sevice.UsersService


@Service
class UsersServiceImpl(private val userRepository: UserRepository): UsersService {
    override fun findAllSortedById(): Iterable<UserDTO> {
        val sort = Sort.by("id").ascending()
        return userRepository.findAll(sort)
                .mapNotNull(::UserDTO)
    }
}