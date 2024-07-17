package bj.ifri.mlcontestapi.modules.models.controllers

import bj.ifri.mlcontestapi.modules.models.dto.TagDto
import bj.ifri.mlcontestapi.modules.models.dto.TagFilterDto
import bj.ifri.mlcontestapi.modules.models.services.TagService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tags")
class TagsController(private val tagService: TagService) {
    @GetMapping
    fun list(tagFilterDto: TagFilterDto): Page<TagDto> {
        return tagService.list(tagFilterDto)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<TagDto> {
        val tag = tagService.getById(id)
        return if (tag == null) ResponseEntity.notFound().build() else ResponseEntity.ok(tag)
    }
}