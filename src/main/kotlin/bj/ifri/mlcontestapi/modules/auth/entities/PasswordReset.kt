package bj.ifri.mlcontestapi.modules.auth.entities

import bj.ifri.mlcontestapi.modules.user.entities.User
import jakarta.persistence.*
import java.util.Date

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
    var user: User? = null,
    @Column(name = "userId")
    var userId: Long,
    var expirationDate: Date
)