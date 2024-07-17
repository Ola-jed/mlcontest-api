package bj.ifri.mlcontestapi.modules.models.dto

import bj.ifri.mlcontestapi.modules.models.entities.Category

class CategoryDto(
    var id: Long,
    var name: String
) {
    companion object {
        fun fromCategory(category: Category): CategoryDto = CategoryDto(category.id!!, category.name)
    }
}
