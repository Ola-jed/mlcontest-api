package com.ola.mlcontestapi.modules.models.repositories

import com.ola.mlcontestapi.modules.models.entities.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CategoryRepository : JpaRepository<Category, Long> {
    @Query("select c from Category c")
    override fun findAll(pageable: Pageable): Page<Category>


    @Query("select c from Category c where upper(c.name) like upper(concat('%', ?1, '%'))")
    fun findByName(name: String, pageable: Pageable): Page<Category>

    @Query("select c from Category c where c.name in ?1")
    fun findByNameIn(names: List<String>): List<Category>
}