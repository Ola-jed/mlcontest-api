package com.ola.mlcontestapi.modules.auth.repositories

import com.ola.mlcontestapi.modules.auth.entities.PasswordReset
import org.springframework.data.repository.CrudRepository

interface PasswordResetRepository : CrudRepository<PasswordReset, Long> {
    fun existsByCode(code: String): Boolean
    fun deleteByCode(code: String)
    fun findByCode(code: String): PasswordReset?
}