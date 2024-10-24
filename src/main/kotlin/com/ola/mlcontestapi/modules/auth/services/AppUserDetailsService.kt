package com.ola.mlcontestapi.modules.auth.services

import com.ola.mlcontestapi.modules.auth.entities.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component


@Component
class AppUserDetailsService(private val userRepository: com.ola.mlcontestapi.modules.user.repositories.UserRepository) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByEmail(username!!) ?: throw UsernameNotFoundException("User $username not found")
        return CustomUserDetails(user)
    }
}