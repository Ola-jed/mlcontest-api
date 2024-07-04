package bj.ifri.mlcontestapi.modules.user.services

import bj.ifri.mlcontestapi.common.services.FileUploadService
import bj.ifri.mlcontestapi.modules.user.dto.UserPatchDto
import bj.ifri.mlcontestapi.modules.user.entities.User
import bj.ifri.mlcontestapi.modules.user.repositories.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.nio.file.Files
import java.nio.file.Paths
import java.security.Principal


@Service
class UserService(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val fileUploadService: FileUploadService
) {
    @Value("\${file.upload-dir}")
    private val uploadFolder: String? = null

    fun getCurrentUser(principal: Principal): User {
        return userRepository.findByEmail(principal.name) ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun patchUser(userPatchDto: UserPatchDto, principal: Principal): User {
        val currentUser = getCurrentUser(principal)

        if (!userPatchDto.username.isNullOrBlank()) {
            currentUser.username = userPatchDto.username
        }

        if (userPatchDto.newPassword != null) {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(currentUser.email, userPatchDto.currentPassword)
            )

            if (authentication.isAuthenticated) {
                currentUser.password = passwordEncoder.encode(userPatchDto.newPassword)
            } else {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
            }
        }

        if (userPatchDto.photo != null && !userPatchDto.photo.isEmpty) {
            if (currentUser.photoUrl != null) {
                Files.delete(Paths.get(uploadFolder + currentUser.photoUrl))
            }

            currentUser.photoUrl = fileUploadService.uploadFile(userPatchDto.photo)
        }

        userRepository.save(currentUser)
        return currentUser
    }

    fun deleteUser(principal: Principal) {
        userRepository.deleteByEmail(principal.name)
    }
}