package bj.ifri.mlcontestapi.modules.user.repositories

import bj.ifri.mlcontestapi.modules.user.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String?): User?
    fun existsByEmail(email: String): Boolean
}