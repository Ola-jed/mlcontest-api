package com.ola.mlcontestapi.modules.auth.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "app_password_reset")
class PasswordReset(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    @Column(unique = true)
    var code: String,
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var user: com.ola.mlcontestapi.modules.user.entities.User? = null,
    @Column(name = "userId")
    var userId: Long,
    var expirationDate: Date
)