package bj.ifri.mlcontestapi.modules.forum.entities

import bj.ifri.mlcontestapi.modules.models.entities.Model
import bj.ifri.mlcontestapi.modules.user.entities.User
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "app_discussion")
class Discussion(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var title: String,
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdById")
    var createdBy: User? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelId")
    var model: Model? = null,
)