package com.ola.mlcontestapi.modules.auth.entities

import java.util.*

data class JwtTokenDetails(
    val token: String,
    val expirationDate: Date
)