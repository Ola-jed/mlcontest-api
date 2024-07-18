package bj.ifri.mlcontestapi.modules.forum.dto

import jakarta.validation.constraints.NotBlank

data class DiscussionCreateDto(
    @field:NotBlank(message = "Title required")
    var title: String
)