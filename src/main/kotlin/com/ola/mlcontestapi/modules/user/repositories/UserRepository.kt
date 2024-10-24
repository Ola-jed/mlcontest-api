package com.ola.mlcontestapi.modules.user.repositories

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<com.ola.mlcontestapi.modules.user.entities.User, Long> {
    fun deleteByEmail(email: String)
    fun findByEmail(email: String?): com.ola.mlcontestapi.modules.user.entities.User?
    fun existsByEmail(email: String): Boolean
}