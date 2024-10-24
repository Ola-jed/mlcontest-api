package com.ola.mlcontestapi.modules.models.dto

import com.ola.mlcontestapi.modules.models.entities.Dataset.FileType

class DatasetDto(
    var id: Long? = null,
    var fileType: FileType,
    var fileSize: Long,
    var fileUrl: String,
    var itemCount: Int,
    var training: Boolean
) {
    companion object {
        fun fromDataset(dataset: com.ola.mlcontestapi.modules.models.entities.Dataset): DatasetDto = DatasetDto(
            dataset.id!!,
            dataset.fileType,
            dataset.fileSize,
            dataset.fileUrl,
            dataset.itemCount,
            dataset.training
        )
    }
}