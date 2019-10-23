package ru.fmtk.khlystov.culture_code.sevice.dto

data class TwoUsersClosenessDTO(val firstUserId: String,
                                val secondUserId: String,
                                val closeness: Float)