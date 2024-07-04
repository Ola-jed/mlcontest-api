package bj.ifri.mlcontestapi.modules.auth.entities

import java.util.Date

data class JwtTokenDetails(
    val token: String,
    val expirationDate: Date
)