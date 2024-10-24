package com.ola.mlcontestapi.modules.forum.dto

import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero

data class DiscussionFilterDto(
    @field:PositiveOrZero
    var page: Int = 0,
    @field:Positive
    var perPage: Int = 10,
    var search: String? = null,
)
