package bj.ifri.mlcontestapi.modules.forum.dto

import bj.ifri.mlcontestapi.modules.forum.entities.Discussion
import bj.ifri.mlcontestapi.modules.user.dto.UserDto
import java.time.LocalDateTime

data class DiscussionDto(
    var id: Long? = null,
    var title: String,
    var createdAt: LocalDateTime,
    var createdBy: UserDto
) {
    companion object {
        fun fromDiscussion(discussion: Discussion): DiscussionDto = DiscussionDto(
            discussion.id!!,
            discussion.title,
            discussion.createdAt!!,
            UserDto.from(discussion.createdBy!!)
        )
    }
}