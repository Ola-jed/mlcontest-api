package bj.ifri.mlcontestapi.modules.auth.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class PasswordResetDto(
    @field:NotBlank(message = "Password required")
    @field:Pattern(
        regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$",
        message = "Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character"
    )
    val password: String,
    @field:NotBlank(message = "Code required")
    val code: String
)