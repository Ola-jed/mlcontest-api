package com.ola.mlcontestapi.modules.models.specifications

import com.ola.mlcontestapi.modules.models.dto.ModelFilterDto
import com.ola.mlcontestapi.modules.models.entities.Category
import com.ola.mlcontestapi.modules.models.entities.Model
import com.ola.mlcontestapi.modules.models.entities.Tag
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

class ModelSpecification(private val filter: ModelFilterDto) : Specification<Model> {
    override fun toPredicate(root: Root<Model>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate? {
        var predicate: Predicate? = null

        if (filter.search != null) {
            val searchTerm = "%${filter.search}%"
            val namePredicate = criteriaBuilder.like(root.get("name"), searchTerm)
            val descriptionPredicate = criteriaBuilder.like(root.get("description"), searchTerm)
            predicate = criteriaBuilder.or(namePredicate, descriptionPredicate)
        }

        if (filter.tags.isNotEmpty()) {
            val tagsPredicate = root.join<Model, Tag>("tags").`in`(filter.tags)
            predicate = if (predicate == null) tagsPredicate else criteriaBuilder.and(predicate, tagsPredicate)
        }

        if (filter.categories.isNotEmpty()) {
            val categoriesPredicate = root.join<Model, Category>("categories").`in`(filter.categories)
            predicate = if (predicate == null) categoriesPredicate else criteriaBuilder.and(predicate, categoriesPredicate)
        }

        return predicate
    }
}
