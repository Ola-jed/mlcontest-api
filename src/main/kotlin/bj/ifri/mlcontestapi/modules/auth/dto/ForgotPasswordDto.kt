package bj.ifri.mlcontestapi.modules.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ForgotPasswordDto(
    @field:NotBlank(message = "Email required")
    @field:Email(message = "The email should be a valid email")
    val email: String
)
