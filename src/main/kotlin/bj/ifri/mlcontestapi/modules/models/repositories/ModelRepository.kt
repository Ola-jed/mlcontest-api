package bj.ifri.mlcontestapi.modules.models.repositories

import bj.ifri.mlcontestapi.modules.models.entities.Model
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ModelRepository : JpaRepository<Model, Long>, JpaSpecificationExecutor<Model> {
}