package ru.fmtk.khlystov.culture_code.rest.dto

data class AuthResponse(val accessToken: String, val tokenType: String = "Bearer")