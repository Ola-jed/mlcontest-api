package com.ola.mlcontestapi.modules.forum.services

import com.ola.mlcontestapi.modules.forum.dto.MessageCreateDto
import com.ola.mlcontestapi.modules.forum.dto.MessageDto
import com.ola.mlcontestapi.modules.forum.dto.MessageFilterDto
import com.ola.mlcontestapi.modules.forum.dto.MessageUpdateDto
import com.ola.mlcontestapi.modules.forum.entities.Message
import com.ola.mlcontestapi.modules.forum.repositories.DiscussionRepository
import com.ola.mlcontestapi.modules.forum.repositories.MessageRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

@Service
class MessageService(
    private var messageRepository: MessageRepository,
    private val discussionRepository: DiscussionRepository,
    private val userRepository: com.ola.mlcontestapi.modules.user.repositories.UserRepository
) {
    fun list(discussionId: Long, messageFilterDto: MessageFilterDto): Page<MessageDto> {
        val pageable = PageRequest.of(messageFilterDto.page, messageFilterDto.perPage)
        val messages = if (!messageFilterDto.search.isNullOrBlank()) {
            messageRepository.findByDiscussionAndContent(
                discussionRepository.getReferenceById(discussionId),
                messageFilterDto.search!!,
                pageable
            )
        } else {
            messageRepository.findByDiscussion(discussionRepository.getReferenceById(discussionId), pageable)
        }

        return messages.map(MessageDto::fromMessage)
    }

    fun create(discussionId: Long, messageCreateDto: MessageCreateDto, principal: Principal): MessageDto {
        val discussion = discussionRepository.findById(discussionId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        val currentUser = getCurrentUser(principal)
        val replyTo = if (messageCreateDto.replyTo == null) {
            null
        } else {
            messageRepository.findById(messageCreateDto.replyTo!!)
                .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        }

        if(replyTo != null && replyTo.discussion?.id != discussion.id) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }

        val message = Message(
            discussion = discussion,
            content = messageCreateDto.content,
            replyTo = replyTo,
            createdBy = currentUser
        )

        return MessageDto.fromMessage(messageRepository.save(message))
    }

    fun update(messageId: Long, messageUpdateDto: MessageUpdateDto, principal: Principal): MessageDto {
        val message = messageRepository.findById(messageId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        val currentUser = getCurrentUser(principal)
        if (currentUser.id != message.createdBy?.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }

        message.content = messageUpdateDto.content
        return MessageDto.fromMessage(messageRepository.save(message))
    }

    fun delete(messageId: Long, principal: Principal) {
        val message = messageRepository.findById(messageId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        val currentUser = getCurrentUser(principal)
        if (currentUser.id != message.createdBy?.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }

        messageRepository.delete(message)
    }

    private fun getCurrentUser(principal: Principal): com.ola.mlcontestapi.modules.user.entities.User {
        return userRepository.findByEmail(principal.name) ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }
}