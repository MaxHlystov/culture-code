package ru.fmtk.khlystov.culture_code.security.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException : RuntimeException {
    constructor(message: String,cause: Throwable): super(message, cause)
    constructor(message: String): super(message)
}