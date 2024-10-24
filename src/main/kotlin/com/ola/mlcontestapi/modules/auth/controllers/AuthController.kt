package com.ola.mlcontestapi.modules.auth.controllers

import com.ola.mlcontestapi.common.services.EmailSendingService
import com.ola.mlcontestapi.modules.auth.dto.*
import com.ola.mlcontestapi.modules.auth.services.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth")
class AuthController(
    private val authService: AuthService,
    private val emailSendingService: EmailSendingService
) {
    @PostMapping("register")
    fun register(@Valid @RequestBody registerDto: RegisterDto): ResponseEntity<AuthResponseDto> {
        val response = authService.register(registerDto)

        return if (response == null) {
            ResponseEntity.badRequest().build()
        } else {
            ResponseEntity.ok(response)
        }
    }

    @PostMapping("login")
    fun login(@Valid @RequestBody loginDto: LoginDto): ResponseEntity<AuthResponseDto> {
        val authData = authService.login(loginDto)

        return if (authData == null) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build()
        } else {
            ResponseEntity.ok(authData)
        }
    }

    @PostMapping("forgot-password")
    fun forgotPassword(@Valid @RequestBody forgotPasswordDto: ForgotPasswordDto): ResponseEntity<Any> {
        val passwordReset = authService.generateForgotPasswordCode(forgotPasswordDto)
            ?: return ResponseEntity.badRequest().build()
        emailSendingService.sendPasswordResetMail(passwordReset, passwordReset.user!!)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("password-reset")
    fun resetPassword(@Valid @RequestBody passwordResetDto: PasswordResetDto): ResponseEntity<Any> {
        val hasReset = authService.resetPassword(passwordResetDto)

        return if (hasReset) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}