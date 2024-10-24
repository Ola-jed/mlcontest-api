package com.ola.mlcontestapi.modules.forum.repositories

import com.ola.mlcontestapi.modules.forum.entities.Discussion
import com.ola.mlcontestapi.modules.forum.entities.Message
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MessageRepository : JpaRepository<Message, Long> {
    @Query("select m from Message m where m.discussion = ?1 order by m.createdAt desc")
    fun findByDiscussion(discussion: Discussion, pageable: Pageable): Page<Message>

    @Query("select m from Message m where m.discussion = ?1 and upper(m.content) like upper(concat('%', ?2, '%')) order by m.createdAt desc")
    fun findByDiscussionAndContent(
        discussion: Discussion,
        content: String,
        pageable: Pageable
    ): Page<Message>
}