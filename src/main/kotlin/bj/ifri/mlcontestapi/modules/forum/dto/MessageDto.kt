package bj.ifri.mlcontestapi.modules.forum.dto

import bj.ifri.mlcontestapi.modules.forum.entities.Message
import bj.ifri.mlcontestapi.modules.user.entities.User
import java.time.LocalDateTime

data class MessageDto(
    var id: Long,
    var content: String,
    var createdBy: User,
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
