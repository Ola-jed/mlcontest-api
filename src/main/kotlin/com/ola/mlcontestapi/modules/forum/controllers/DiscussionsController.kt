package com.ola.mlcontestapi.modules.forum.controllers

import com.ola.mlcontestapi.modules.forum.dto.DiscussionCreateDto
import com.ola.mlcontestapi.modules.forum.dto.DiscussionDto
import com.ola.mlcontestapi.modules.forum.dto.DiscussionFilterDto
import com.ola.mlcontestapi.modules.forum.dto.DiscussionUpdateDto
import com.ola.mlcontestapi.modules.forum.services.DiscussionService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class DiscussionsController(private val discussionService: DiscussionService) {
    @GetMapping("/models/{id}/discussions")
    fun list(@PathVariable("id") id: Long, @Valid discussionFilterDto: DiscussionFilterDto): Page<DiscussionDto> {
        return discussionService.list(id, discussionFilterDto)
    }

    @PostMapping("/models/{id}/discussions")
    fun create(
        @PathVariable("id") id: Long,
        @RequestBody discussionCreateDto: DiscussionCreateDto,
        principal: Principal
    ): ResponseEntity<DiscussionDto> {
        return ResponseEntity.ok(discussionService.create(id, discussionCreateDto, principal))
    }

    @PatchMapping("/discussions/{id}")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody discussionUpdateDto: DiscussionUpdateDto,
        principal: Principal
    ): ResponseEntity<DiscussionDto> {
        return ResponseEntity.ok(discussionService.update(id, discussionUpdateDto, principal))
    }

    @DeleteMapping("/discussions/{id}")
    fun delete(@PathVariable("id") id: Long, principal: Principal): ResponseEntity<Void> {
        discussionService.delete(id, principal)
        return ResponseEntity.noContent().build()
    }
}