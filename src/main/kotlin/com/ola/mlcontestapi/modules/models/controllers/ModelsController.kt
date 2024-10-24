package com.ola.mlcontestapi.modules.models.controllers

import com.ola.mlcontestapi.modules.models.dto.*
import com.ola.mlcontestapi.modules.models.services.ModelService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/models")
class ModelsController(private val modelService: ModelService) {
    @GetMapping
    fun list(@Valid modelFilterDto: ModelFilterDto): Page<ModelDto> {
        return modelService.list(modelFilterDto)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): ResponseEntity<ModelDto> {
        val model = modelService.getById(id)
        return if (model == null) ResponseEntity.notFound().build() else ResponseEntity.ok(model)
    }

    @PostMapping
    fun create(@RequestBody modelCreateDto: ModelCreateDto, principal: Principal): ResponseEntity<ModelDto> {
        return ResponseEntity.ok(modelService.create(modelCreateDto, principal))
    }

    @PostMapping("/{id}/datasets", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createDataset(
        @PathVariable("id") id: Long,
        @ModelAttribute datasetCreateDto: DatasetCreateDto,
        principal: Principal
    ): ResponseEntity<ModelDto> {
        return ResponseEntity.ok(modelService.createDataset(id, datasetCreateDto, principal))
    }

    @DeleteMapping("{modelId}/datasets/{datasetId}")
    fun deleteDataset(
        @PathVariable("modelId") modelId: Long,
        @PathVariable("datasetId") datasetId: Long,
        principal: Principal
    ): ResponseEntity<ModelDto> {
        modelService.deleteDataset(modelId, datasetId, principal)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody modelPatchDto: ModelPatchDto,
        principal: Principal
    ): ResponseEntity<ModelDto> {
        return ResponseEntity.ok(modelService.patchModel(id, modelPatchDto, principal))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long, principal: Principal): ResponseEntity<Void> {
        modelService.deleteModel(id, principal)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}/image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateImage(
        @PathVariable("id") id: Long,
        @ModelAttribute image: ModelImageDto,
        principal: Principal
    ): ResponseEntity<ModelDto> {
        val updateResult = modelService.updateModelImage(id, image, principal)
        return ResponseEntity.ok(updateResult)
    }
}