package bj.ifri.mlcontestapi.modules.models.services

import bj.ifri.mlcontestapi.common.services.FileUploadService
import bj.ifri.mlcontestapi.modules.models.dto.*
import bj.ifri.mlcontestapi.modules.models.entities.ColumnDescription
import bj.ifri.mlcontestapi.modules.models.entities.Dataset
import bj.ifri.mlcontestapi.modules.models.entities.Model
import bj.ifri.mlcontestapi.modules.models.repositories.DatasetRepository
import bj.ifri.mlcontestapi.modules.models.repositories.ModelRepository
import bj.ifri.mlcontestapi.modules.models.specifications.ModelSpecification
import bj.ifri.mlcontestapi.modules.user.entities.User
import bj.ifri.mlcontestapi.modules.user.repositories.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

@Service
class ModelService(
    private val modelRepository: ModelRepository,
    private val tagService: TagService,
    private val categoryService: CategoryService,
    private val userRepository: UserRepository,
    private val fileUploadService: FileUploadService,
    private val datasetRepository: DatasetRepository
) {
    fun list(modelFilterDto: ModelFilterDto): Page<ModelDto> {
        val spec = ModelSpecification(modelFilterDto)
        val pageable = PageRequest.of(modelFilterDto.page, modelFilterDto.perPage)
        val page = modelRepository.findAll(spec, pageable)
        return page.map(ModelDto::fromModel)
    }

    fun getById(id: Long): ModelDto? {
        return modelRepository.findById(id)
            .orElse(null)
            ?.let(ModelDto::fromModel)
    }

    fun create(modelCreateDto: ModelCreateDto, principal: Principal): ModelDto? {
        val tags = tagService.createInBatch(modelCreateDto.tags)
        val categories = categoryService.createInBatch(modelCreateDto.categories)

        val model = Model(
            name = modelCreateDto.name,
            description = modelCreateDto.description,
            tags = tags.toMutableList(),
            categories = categories.toMutableList(),
            columnDescriptions = modelCreateDto.columnDescriptions.map {
                ColumnDescription(
                    name = it.name,
                    description = it.description,
                    dependant = it.dependant,
                    note = it.note
                )
            }.toMutableList(),
            createdBy = getCurrentUser(principal).id!!
        )

        val savedModel = modelRepository.save(model)
        return ModelDto.fromModel(savedModel)
    }

    fun updateModelImage(id: Long, modelImageDto: ModelImageDto, principal: Principal): ModelDto {
        val model = modelRepository.findById(id).orElse(null) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val currentUser = getCurrentUser(principal)

        if (model.createdBy != currentUser.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }

        if (model.imagePublicId != null) {
            fileUploadService.deleteImage(model.imagePublicId!!)
        }

        val imageUrlAndId = fileUploadService.uploadImage(modelImageDto.image)
        model.imageUrl = imageUrlAndId.first
        model.imagePublicId = imageUrlAndId.second
        return ModelDto.fromModel(modelRepository.save(model))
    }

    fun patchModel(id: Long, modelPatchDto: ModelPatchDto, principal: Principal): ModelDto {
        val model = modelRepository.findById(id).orElse(null) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val currentUser = getCurrentUser(principal)

        if (model.createdBy != currentUser.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }

        if (modelPatchDto.name != null) {
            model.name = modelPatchDto.name
        }

        if (modelPatchDto.description != null) {
            model.description = modelPatchDto.description
        }

        if (modelPatchDto.tags != null) {
            val tags = tagService.createInBatch(modelPatchDto.tags)
            model.tags = tags.toMutableList()
        }

        if (modelPatchDto.categories != null) {
            val categories = categoryService.createInBatch(modelPatchDto.categories)
            model.categories = categories.toMutableList()
        }

        if (modelPatchDto.columnDescriptions != null) {
            val columnDescriptions = modelPatchDto.columnDescriptions.map {
                ColumnDescription(
                    name = it.name,
                    description = it.description,
                    dependant = it.dependant,
                    note = it.note
                )
            }
            model.columnDescriptions = columnDescriptions.toMutableList()
        }

        return ModelDto.fromModel(modelRepository.save(model))
    }

    fun deleteModel(id: Long, principal: Principal) {
        val model = modelRepository.findById(id).orElse(null) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val currentUser = getCurrentUser(principal)

        if (model.createdBy != currentUser.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }

        modelRepository.delete(model)
    }

    fun createDataset(id: Long, datasetCreateDto: DatasetCreateDto, principal: Principal): ModelDto {
        val model = modelRepository.findById(id).orElse(null) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val currentUser = getCurrentUser(principal)
        if (model.createdBy != currentUser.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }

        val datasetPathAndSize = fileUploadService.uploadDataset(datasetCreateDto.file)
        val dataset = Dataset(
            fileType = datasetCreateDto.fileType,
            fileSize = datasetPathAndSize.second,
            fileUrl = datasetPathAndSize.first,
            fileId = datasetPathAndSize.first,
            itemCount = datasetCreateDto.itemCount,
            training = datasetCreateDto.training
        )

        model.datasets.add(dataset)
        return ModelDto.fromModel(modelRepository.save(model))
    }

    fun deleteDataset(modelId: Long, datasetId: Long, principal: Principal) {
        val model = modelRepository.findById(modelId).orElse(null) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val currentUser = getCurrentUser(principal)
        if (model.createdBy != currentUser.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }

        val dataset = model.datasets.find { it.id == datasetId } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        model.datasets.remove(dataset)
        datasetRepository.delete(dataset)
    }

    private fun getCurrentUser(principal: Principal): User {
        return userRepository.findByEmail(principal.name) ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }
}