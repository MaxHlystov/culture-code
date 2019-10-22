package ru.fmtk.khlystov.culture_code.rest.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class LoginRequest(
        @NotBlank val name: String,
        @NotBlank val password: String
)