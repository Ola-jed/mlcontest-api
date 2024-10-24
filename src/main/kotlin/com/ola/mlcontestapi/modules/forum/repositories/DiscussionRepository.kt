package com.ola.mlcontestapi.modules.forum.repositories

import com.ola.mlcontestapi.modules.forum.entities.Discussion
import com.ola.mlcontestapi.modules.models.entities.Model
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DiscussionRepository : JpaRepository<Discussion, Long> {
    @Query("select d from Discussion d where d.model = ?1 order by d.createdAt desc")
    fun findByModel(model: Model, pageable: Pageable): Page<Discussion>

    @Query("select d from Discussion d where d.model = ?1 and upper(d.title) like upper(concat('%', ?2, '%')) order by d.createdAt desc")
    fun findByTitleAndModel(model: Model, title: String, pageable: Pageable): Page<Discussion>
}