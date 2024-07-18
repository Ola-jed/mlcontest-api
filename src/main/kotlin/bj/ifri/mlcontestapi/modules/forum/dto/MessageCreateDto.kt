package bj.ifri.mlcontestapi.modules.forum.dto

import jakarta.validation.constraints.NotBlank

data class MessageCreateDto(
    @field:NotBlank(message = "Content required")
    var content: String,
    var replyTo: Long? = null
)
