package ru.fmtk.khlystov.culture_code.security

import org.springframework.security.core.GrantedAuthority

data class Authority(val name: String): GrantedAuthority {
    override fun getAuthority(): String = name
}