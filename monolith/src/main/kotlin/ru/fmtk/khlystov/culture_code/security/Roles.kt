package ru.fmtk.khlystov.culture_code.security

enum class Roles(val role: String) {
    User("ROLE_USER"),
    Admin("ROLE_ADMIN");

    override fun toString(): String = role
}