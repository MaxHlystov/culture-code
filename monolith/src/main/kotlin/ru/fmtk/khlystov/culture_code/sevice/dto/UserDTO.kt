package ru.fmtk.khlystov.culture_code.sevice.dto

data class UserDTO(var id: String?,
                   val name: String,
                   val email: String = "") {
}