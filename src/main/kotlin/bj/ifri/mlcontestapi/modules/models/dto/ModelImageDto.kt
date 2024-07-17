package bj.ifri.mlcontestapi.modules.models.dto

import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

class ModelImageDto (
    @field:NotNull(message = "The image is required")
    val image: MultipartFile
)