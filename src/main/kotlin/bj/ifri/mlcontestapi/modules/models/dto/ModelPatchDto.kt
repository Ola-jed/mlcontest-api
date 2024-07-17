package bj.ifri.mlcontestapi.modules.models.dto

data class ModelPatchDto(
    val name: String? = null,
    val description: String? = null,
    val tags: List<String>? = null,
    val categories: List<String>? = null,
    val columnDescriptions: List<ColumnDescriptionCreateDto>? = null
)