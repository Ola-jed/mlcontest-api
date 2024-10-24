package com.ola.mlcontestapi.modules.forum.controllers

import com.ola.mlcontestapi.modules.forum.dto.MessageCreateDto
import com.ola.mlcontestapi.modules.forum.dto.MessageDto
import com.ola.mlcontestapi.modules.forum.dto.MessageFilterDto
import com.ola.mlcontestapi.modules.forum.dto.MessageUpdateDto
import com.ola.mlcontestapi.modules.forum.services.MessageService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class MessagesController(private val messageService: MessageService) {
    @GetMapping("/discussions/{id}/messages")
    fun list(@PathVariable("id") id: Long, @Valid messageFilterDto: MessageFilterDto): Page<MessageDto> {
        return messageService.list(id, messageFilterDto)
    }

    @PostMapping("/discussions/{id}/messages")
    fun create(
        @PathVariable("id") id: Long,
        @RequestBody messageCreateDto: MessageCreateDto,
        principal: Principal
    ): ResponseEntity<MessageDto> {
        return ResponseEntity.ok(messageService.create(id, messageCreateDto, principal))
    }

    @PutMapping("/messages/{id}")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody messageUpdateDto: MessageUpdateDto,
        principal: Principal
    ): ResponseEntity<MessageDto> {
        return ResponseEntity.ok(messageService.update(id, messageUpdateDto, principal))
    }

    @DeleteMapping("/messages/{id}")
    fun delete(@PathVariable("id") id: Long, principal: Principal): ResponseEntity<Void> {
        messageService.delete(id, principal)
        return ResponseEntity.noContent().build()
    }
}