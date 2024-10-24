package com.ola.mlcontestapi.modules.models.repositories

import com.ola.mlcontestapi.modules.models.entities.Model
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ModelRepository : JpaRepository<Model, Long>, JpaSpecificationExecutor<Model> {
}