package com.ola.mlcontestapi.modules.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginDto(
    @field:NotBlank(message = "Email required")
    @field:Email(message = "The email should be a valid email")
    val email: String,
    @field:NotBlank(message = "Password required")
    val password: String
)
