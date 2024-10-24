package com.ola.mlcontestapi.modules.forum.dto

import com.ola.mlcontestapi.modules.forum.entities.Discussion
import com.ola.mlcontestapi.modules.user.dto.UserDto
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