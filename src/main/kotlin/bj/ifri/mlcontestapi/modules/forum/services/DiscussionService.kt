package bj.ifri.mlcontestapi.modules.forum.services

import bj.ifri.mlcontestapi.modules.forum.dto.DiscussionCreateDto
import bj.ifri.mlcontestapi.modules.forum.dto.DiscussionDto
import bj.ifri.mlcontestapi.modules.forum.dto.DiscussionFilterDto
import bj.ifri.mlcontestapi.modules.forum.dto.DiscussionUpdateDto
import bj.ifri.mlcontestapi.modules.forum.entities.Discussion
import bj.ifri.mlcontestapi.modules.forum.repositories.DiscussionRepository
import bj.ifri.mlcontestapi.modules.models.repositories.ModelRepository
import bj.ifri.mlcontestapi.modules.user.entities.User
import bj.ifri.mlcontestapi.modules.user.repositories.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

@Service
class DiscussionService(
    private var discussionRepository: DiscussionRepository,
    private var modelRepository: ModelRepository,
    private val userRepository: UserRepository
) {
    fun list(modelId: Long, discussionFilterDto: DiscussionFilterDto): Page<DiscussionDto> {
        val pageable = PageRequest.of(discussionFilterDto.page, discussionFilterDto.perPage)
        val discussions = if (!discussionFilterDto.search.isNullOrBlank()) {
            discussionRepository.findByTitleAndModel(
                modelRepository.getReferenceById(modelId),
                discussionFilterDto.search!!,
                pageable
            )
        } else {
            discussionRepository.findByModel(modelRepository.getReferenceById(modelId), pageable)
        }

        return discussions.map(DiscussionDto::fromDiscussion)
    }

    fun create(modelId: Long, discussionCreateDto: DiscussionCreateDto, principal: Principal): DiscussionDto {
        val model = modelRepository.findById(modelId).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        val currentUser = getCurrentUser(principal)
        val discussion = Discussion(
            title = discussionCreateDto.title,
            model = model,
            createdBy = currentUser
        )
        return DiscussionDto.fromDiscussion(discussionRepository.save(discussion))
    }

    fun update(discussionId: Long, discussionUpdateDto: DiscussionUpdateDto, principal: Principal): DiscussionDto {
        val discussion = discussionRepository.findById(discussionId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        val currentUser = getCurrentUser(principal)

        if (currentUser.id != discussion.createdBy?.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }

        discussion.title = discussionUpdateDto.title
        return DiscussionDto.fromDiscussion(discussionRepository.save(discussion))
    }

    fun delete(discussionId: Long, principal: Principal) {
        val discussion = discussionRepository.findById(discussionId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        val currentUser = getCurrentUser(principal)

        if (currentUser.id != discussion.createdBy?.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }

        discussionRepository.delete(discussion)
    }

    private fun getCurrentUser(principal: Principal): User {
        return userRepository.findByEmail(principal.name) ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }
}