package bj.ifri.mlcontestapi.modules.user.dto

import bj.ifri.mlcontestapi.modules.user.entities.User
import java.util.*

data class UserDto(
    var id: Long? = null,
    var username: String,
    var email: String,
    var photoUrl: String? = null,
    var registrationDate: Date
) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(
                user.id,
                user.username,
                user.email,
                user.photoUrl,
                user.registrationDate
            )
        }
    }
}