package ru.fmtk.khlystov.culture_code.sevice

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.fmtk.khlystov.culture_code.sevice.dto.UserDTO

@Service
interface UsersService {
    fun findAllSortedById(): Iterable<UserDTO>
}