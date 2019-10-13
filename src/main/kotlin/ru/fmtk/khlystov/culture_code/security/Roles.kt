package ru.fmtk.khlystov.culture_code.security

enum class Roles(val role: String) {
    User("USER"),
    Admin("ADMIN");

    override fun toString(): String = role
}