package bj.ifri.mlcontestapi.modules.models.dto

import bj.ifri.mlcontestapi.modules.models.entities.Dataset
import bj.ifri.mlcontestapi.modules.models.entities.Dataset.FileType

class DatasetDto(
    var id: Long? = null,
    var fileType: FileType,
    var fileSize: Long,
    var fileUrl: String,
    var itemCount: Int,
) {
    companion object {
        fun fromDataset(dataset: Dataset): DatasetDto = DatasetDto(
            dataset.id!!,
            dataset.fileType,
            dataset.fileSize,
            dataset.fileUrl,
            dataset.itemCount
        )
    }
}