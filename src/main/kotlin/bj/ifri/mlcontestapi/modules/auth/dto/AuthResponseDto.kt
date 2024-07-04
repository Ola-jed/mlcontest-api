package bj.ifri.mlcontestapi.modules.auth.dto

import java.util.Date

data class AuthResponseDto(
    val token: String,
    val expirationDate: Date,
    val id: Long,
    val username: String,
    val email: String,
    val registrationDate: Date,
    val photoUrl: String?
)
