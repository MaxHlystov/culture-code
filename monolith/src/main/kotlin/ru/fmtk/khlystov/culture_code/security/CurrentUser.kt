package ru.fmtk.khlystov.culture_code.security

import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.lang.annotation.Documented

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Documented
@AuthenticationPrincipal
annotation class CurrentUser