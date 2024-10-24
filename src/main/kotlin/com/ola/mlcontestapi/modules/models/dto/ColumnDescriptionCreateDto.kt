package com.ola.mlcontestapi.modules.models.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ColumnDescriptionCreateDto(
    @field:NotBlank(message = "Name required")
    val name: String,
    @field:NotBlank(message = "Description required")
    val description: String,
    @field:NotNull(message = "Dependant information required")
    val dependant: Boolean,
    val note: String? = null
)