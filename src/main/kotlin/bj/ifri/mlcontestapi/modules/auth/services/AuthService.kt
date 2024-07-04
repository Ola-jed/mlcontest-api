package bj.ifri.mlcontestapi.modules.auth.services

import bj.ifri.mlcontestapi.modules.auth.dto.*
import bj.ifri.mlcontestapi.modules.auth.entities.PasswordReset
import bj.ifri.mlcontestapi.modules.auth.repositories.PasswordResetRepository
import bj.ifri.mlcontestapi.modules.user.entities.User
import bj.ifri.mlcontestapi.modules.user.repositories.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


@Service
class AuthService(
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val passwordResetRepository: PasswordResetRepository,
    private val authenticationManager: AuthenticationManager
) {
    fun register(registerDto: RegisterDto): AuthResponseDto? {
        if (userRepository.existsByEmail(registerDto.email)) {
            return null
        }

        val user = userRepository.save(
            User(
                username = registerDto.username,
                email = registerDto.email,
                password = passwordEncoder.encode(registerDto.password),
                registrationDate = Date()
            )
        )

        val jwt = jwtService.generateToken(user.email)

        return AuthResponseDto(
            token = jwt.token,
            expirationDate = jwt.expirationDate,
            id = user.id!!,
            username = user.username,
            email = user.email,
            registrationDate = user.registrationDate,
            photoUrl = user.photoUrl
        )
    }

    fun login(loginDto: LoginDto): AuthResponseDto? {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
        )

        if (!authentication.isAuthenticated) {
            return null
        }

        val user = userRepository.findByEmail(loginDto.email) ?: return null
        val jwt = jwtService.generateToken(loginDto.email)

        return AuthResponseDto(
            token = jwt.token,
            expirationDate = jwt.expirationDate,
            id = user.id!!,
            username = user.username,
            email = user.email,
            registrationDate = user.registrationDate,
            photoUrl = user.photoUrl
        )
    }

    fun generateForgotPasswordCode(forgotPasswordDto: ForgotPasswordDto): PasswordReset? {
        val user = userRepository.findByEmail(forgotPasswordDto.email) ?: return null
        var code: String

        do {
            code = (10000..99999).random().toString()
        } while (passwordResetRepository.existsByCode(code))

        val currentDate = Date()
        val currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault())
        val newDateTime = currentDateTime.plusMinutes(30)

        return passwordResetRepository.save(
            PasswordReset(
                code = code,
                userId = user.id!!,
                user = user,
                expirationDate = Date.from(newDateTime.atZone(ZoneId.systemDefault()).toInstant())
            )
        )
    }

    fun resetPassword(passwordResetDto: PasswordResetDto): Boolean {
        val passwordReset = passwordResetRepository.findByCode(passwordResetDto.code) ?: return false

        if (passwordReset.expirationDate.after(Date())) {
            val user = passwordReset.user!!
            user.password = passwordEncoder.encode(passwordResetDto.password)
            userRepository.save(user)
            passwordResetRepository.delete(passwordReset)
            return true
        }

        passwordResetRepository.delete(passwordReset)
        return false
    }
}