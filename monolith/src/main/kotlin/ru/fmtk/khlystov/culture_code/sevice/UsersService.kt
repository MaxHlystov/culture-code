package ru.fmtk.khlystov.culture_code.sevice

import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.rest.dto.UserDTO

@Service
interface UsersService {
    fun findAllSortedById(): Iterable<UserDTO>
}