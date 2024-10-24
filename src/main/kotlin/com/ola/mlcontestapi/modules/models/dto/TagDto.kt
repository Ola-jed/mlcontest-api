package com.ola.mlcontestapi.modules.models.dto

import com.ola.mlcontestapi.modules.models.entities.Tag

class TagDto(
    var id: Long,
    var name: String
) {
    companion object {
        fun fromTag(tag: Tag): TagDto = TagDto(tag.id!!, tag.name)
    }
}