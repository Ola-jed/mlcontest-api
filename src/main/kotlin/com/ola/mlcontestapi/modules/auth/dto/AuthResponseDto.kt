package com.ola.mlcontestapi.modules.auth.dto

import java.util.*

data class AuthResponseDto(
    val token: String,
    val expirationDate: Date,
    val id: Long,
    val username: String,
    val email: String,
    val registrationDate: Date,
    val photoUrl: String?
)
