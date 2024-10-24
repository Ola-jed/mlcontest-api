package com.ola.mlcontestapi.modules.models.entities

import jakarta.persistence.*

@Entity
@Table(name = "app_category")
class Category (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    @Column(nullable = false, unique = true)
    var name: String
)