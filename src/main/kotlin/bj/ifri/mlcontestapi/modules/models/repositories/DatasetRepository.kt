package bj.ifri.mlcontestapi.modules.models.repositories

import bj.ifri.mlcontestapi.modules.models.entities.Dataset
import org.springframework.data.jpa.repository.JpaRepository

interface DatasetRepository : JpaRepository<Dataset, Long> {
}