package bj.ifri.mlcontestapi.modules.models.controllers

import bj.ifri.mlcontestapi.modules.models.dto.CategoryDto
import bj.ifri.mlcontestapi.modules.models.dto.CategoryFilterDto
import bj.ifri.mlcontestapi.modules.models.services.CategoryService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoriesController(private val categoryService: CategoryService) {
    @GetMapping
    fun list(categoryFilterDto: CategoryFilterDto): Page<CategoryDto> {
        return categoryService.list(categoryFilterDto)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<CategoryDto> {
        val category = categoryService.getById(id)
        return if (category == null) ResponseEntity.notFound().build() else ResponseEntity.ok(category)
    }
}