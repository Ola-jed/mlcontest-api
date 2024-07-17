package bj.ifri.mlcontestapi.modules.models.repositories

import bj.ifri.mlcontestapi.modules.models.entities.ColumnDescription
import org.springframework.data.repository.CrudRepository

interface ColumnDescriptionRepository : CrudRepository<ColumnDescription, Long> {
}