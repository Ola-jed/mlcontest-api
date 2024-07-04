package bj.ifri.mlcontestapi.modules.user.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "app_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var username: String,
    @Column(unique = true)
    var email: String,
    var password: String,
    var photoUrl: String? = null,
    var registrationDate: Date
)