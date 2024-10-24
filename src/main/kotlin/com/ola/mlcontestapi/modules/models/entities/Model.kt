package com.ola.mlcontestapi.modules.models.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "app_model")
class Model(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var name: String,
    var description: String,
    var imageUrl: String? = null,
    var imagePublicId: String? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "model_tag",
        joinColumns = [JoinColumn(name = "model_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")]
    )
    var tags: MutableList<Tag> = mutableListOf(),
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "model_category",
        joinColumns = [JoinColumn(name = "model_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "category_id", referencedColumnName = "id")]
    )
    var categories: MutableList<Category> = mutableListOf(),
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var datasets: MutableList<com.ola.mlcontestapi.modules.models.entities.Dataset> = mutableListOf(),
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var columnDescriptions: MutableList<ColumnDescription> = mutableListOf(),
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,
    @Column(nullable = false)
    var createdBy: Long
)