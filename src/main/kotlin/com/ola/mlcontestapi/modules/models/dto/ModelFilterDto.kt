package com.ola.mlcontestapi.modules.models.dto

import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero

data class ModelFilterDto(
    @field:PositiveOrZero
    var page: Int = 0,
    @field:Positive
    var perPage: Int = 10,
    var search: String? = null,
    var tags: List<Long> = listOf(),
    var categories: List<Long> = listOf()
)
