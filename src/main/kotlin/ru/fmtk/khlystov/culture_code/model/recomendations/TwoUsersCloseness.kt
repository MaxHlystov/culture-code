package ru.fmtk.khlystov.culture_code.model.recomendations


data class TwoUsersCloseness(val firstUserId: String,
                        val secondUserId: String,
                        val closeness: Float) {
}