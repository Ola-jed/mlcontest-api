package com.ola.mlcontestapi.modules.models.repositories

import com.ola.mlcontestapi.modules.models.entities.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TagRepository : JpaRepository<Tag, Long> {
    @Query("select t from Tag t")
    override fun findAll(pageable: Pageable): Page<Tag>


    @Query("select t from Tag t where upper(t.name) like upper(concat('%', ?1, '%'))")
    fun findByName(name: String, pageable: Pageable): Page<Tag>

    @Query("select t from Tag t where t.name in ?1")
    fun findByNameIn(names: List<String>): List<Tag>
}