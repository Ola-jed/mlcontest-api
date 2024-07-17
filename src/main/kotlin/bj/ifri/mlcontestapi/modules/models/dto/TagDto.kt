package bj.ifri.mlcontestapi.modules.models.dto

import bj.ifri.mlcontestapi.modules.models.entities.Tag

class TagDto(
    var id: Long,
    var name: String
) {
    companion object {
        fun fromTag(tag: Tag): TagDto = TagDto(tag.id!!, tag.name)
    }
}