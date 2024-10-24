package com.ola.mlcontestapi.modules.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class RegisterDto(
    @field:NotBlank(message = "Username required")
    val username: String,
    @field:NotBlank(message = "Email required")
    @field:Email(message = "The email should be a valid email")
    val email: String,
    @field:NotBlank(message = "Password required")
    @field:Pattern(
        regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$",
        message = "Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character"
    )
    val password: String
)
