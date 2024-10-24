package com.ola.mlcontestapi.modules.models.dto

import com.ola.mlcontestapi.modules.models.entities.ColumnDescription

class ColumnDescriptionDto(
    var id: Long,
    var name: String,
    var description: String,
    var dependant: Boolean,
    var note: String? = null
) {
    companion object {
        fun fromColumnDescription(columnDescription: ColumnDescription): ColumnDescriptionDto = ColumnDescriptionDto(
            columnDescription.id!!,
            columnDescription.name,
            columnDescription.description,
            columnDescription.dependant,
            columnDescription.note
        )
    }
}