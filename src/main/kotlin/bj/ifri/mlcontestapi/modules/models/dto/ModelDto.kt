package bj.ifri.mlcontestapi.modules.models.dto

import bj.ifri.mlcontestapi.modules.models.entities.Model
import java.time.LocalDateTime

class ModelDto(
    var id: Long,
    var name: String,
    var description: String,
    var imageUrl: String? = null,
    var tags: List<TagDto> = listOf(),
    var categories: List<CategoryDto> = listOf(),
    var datasets: List<DatasetDto> = listOf(),
    var columnDescriptions: List<ColumnDescriptionDto> = listOf(),
    var createdAt: LocalDateTime
) {
    companion object {
        fun fromModel(model: Model): ModelDto {
            return ModelDto(
                model.id!!,
                model.name,
                model.description,
                model.imageUrl,
                model.tags.map(TagDto::fromTag),
                model.categories.map(CategoryDto::fromCategory),
                model.datasets.map(DatasetDto::fromDataset),
                model.columnDescriptions.map(ColumnDescriptionDto::fromColumnDescription),
                model.createdAt!!
            )
        }
    }
}