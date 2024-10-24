package com.ola.mlcontestapi.modules.models.repositories

import com.ola.mlcontestapi.modules.models.entities.ColumnDescription
import org.springframework.data.repository.CrudRepository

interface ColumnDescriptionRepository : CrudRepository<ColumnDescription, Long> {
}