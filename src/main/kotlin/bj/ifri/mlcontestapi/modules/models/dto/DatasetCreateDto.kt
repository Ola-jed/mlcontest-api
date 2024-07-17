package bj.ifri.mlcontestapi.modules.models.dto

import bj.ifri.mlcontestapi.modules.models.entities.Dataset.FileType
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

data class DatasetCreateDto(
    @field:NotNull(message = "Filetype required")
    val fileType: FileType,
    @field:NotNull(message = "Item count required")
    val itemCount: Int,
    @field:NotNull(message = "Public flag required")
    val public: Boolean,
    @field:NotNull(message = "The file is required")
    val file: MultipartFile
)
