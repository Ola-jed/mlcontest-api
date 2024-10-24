package com.ola.mlcontestapi.modules.models.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface DatasetRepository : JpaRepository<com.ola.mlcontestapi.modules.models.entities.Dataset, Long> {
}