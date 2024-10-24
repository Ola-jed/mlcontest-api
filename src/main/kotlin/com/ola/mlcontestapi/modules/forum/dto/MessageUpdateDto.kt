package com.ola.mlcontestapi.modules.forum.dto

import jakarta.validation.constraints.NotBlank

data class MessageUpdateDto(
    @field:NotBlank(message = "Content required")
    var content: String
)
