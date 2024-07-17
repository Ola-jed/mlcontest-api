package bj.ifri.mlcontestapi.modules.models.services

import bj.ifri.mlcontestapi.modules.models.dto.CategoryDto
import bj.ifri.mlcontestapi.modules.models.dto.CategoryFilterDto
import bj.ifri.mlcontestapi.modules.models.entities.Category
import bj.ifri.mlcontestapi.modules.models.repositories.CategoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    fun list(categoryFilterDto: CategoryFilterDto): Page<CategoryDto> {
        val pageable = PageRequest.of(categoryFilterDto.page, categoryFilterDto.perPage)
        val categories = if (!categoryFilterDto.search.isNullOrBlank()) {
            categoryRepository.findByName(categoryFilterDto.search!!, pageable)
        } else {
            categoryRepository.findAll(pageable)
        }

        return categories.map(CategoryDto::fromCategory)
    }

    fun getById(id: Long): CategoryDto? {
        return categoryRepository.findById(id)
            .orElse(null)
            ?.let(CategoryDto::fromCategory)
    }

    fun createInBatch(categories: List<String>): List<Category> {
        val found = categoryRepository.findByNameIn(categories)
        val namesFound = found.map { it.name }
        val namesToStore = categories.filter { !namesFound.contains(it) }
        val categoriesToStore = namesToStore.map { Category(name = it) }

        val stored = categoryRepository.saveAll(categoriesToStore)
        return stored + found
    }
}