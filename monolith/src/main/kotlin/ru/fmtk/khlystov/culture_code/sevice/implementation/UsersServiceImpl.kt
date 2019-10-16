package ru.fmtk.khlystov.culture_code.sevice.implementation

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.repository.UserRepository
import ru.fmtk.khlystov.culture_code.sevice.UsersService
import ru.fmtk.khlystov.culture_code.sevice.dto.UserDTO

@Service
class UsersServiceImpl(private val userRepository: UserRepository): UsersService {
    override fun findAll(sort: Sort): Iterable<UserDTO> {
        return userRepository.findAll(sort)
                .mapNotNull { user -> UserDTO(user.id, user.name, user.email) }
    }
}