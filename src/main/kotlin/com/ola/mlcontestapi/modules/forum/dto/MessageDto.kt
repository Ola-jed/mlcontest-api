package com.ola.mlcontestapi.modules.forum.dto

import com.ola.mlcontestapi.modules.forum.entities.Message
import java.time.LocalDateTime

data class MessageDto(
    var id: Long,
    var content: String,
    var createdBy: com.ola.mlcontestapi.modules.user.entities.User,
    var createdAt: LocalDateTime,
    var replyToId: Long? = null
) {
    companion object {
        fun fromMessage(message: Message): MessageDto = MessageDto(
            message.id!!,
            message.content,
            message.createdBy!!,
            message.createdAt!!,
            message.replyTo?.id
        )
    }
}
