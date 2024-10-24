package com.ola.mlcontestapi.modules.user.dto

import org.springframework.web.multipart.MultipartFile

data class UserPatchDto(
    val username: String? = null,
    val currentPassword: String? = null,
    val newPassword: String? = null,
    val photo: MultipartFile? = null
)