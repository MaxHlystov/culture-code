package ru.fmtk.khlystov.culture_code.security

enum class Roles(val role: String) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    override fun toString(): String = role
}