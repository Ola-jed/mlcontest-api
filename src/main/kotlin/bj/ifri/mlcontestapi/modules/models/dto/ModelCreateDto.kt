package bj.ifri.mlcontestapi.modules.models.dto

import jakarta.validation.constraints.NotBlank

data class ModelCreateDto(
    @field:NotBlank(message = "Name required")
    val name: String,
    @field:NotBlank(message = "Description required")
    val description: String,
    val tags: List<String>,
    val categories: List<String>,
    val columnDescriptions: List<ColumnDescriptionCreateDto>
)