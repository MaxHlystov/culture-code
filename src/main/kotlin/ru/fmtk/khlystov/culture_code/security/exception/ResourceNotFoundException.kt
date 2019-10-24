package ru.fmtk.khlystov.culture_code.security.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(resourceName: String,
                                fieldName: String,
                                fieldValue: Any
): RuntimeException("$resourceName not found with $fieldName : '$fieldValue'")