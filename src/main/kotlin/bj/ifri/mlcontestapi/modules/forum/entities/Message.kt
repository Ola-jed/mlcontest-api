package bj.ifri.mlcontestapi.modules.forum.entities

import bj.ifri.mlcontestapi.modules.user.entities.User
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "app_message")
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    @Column(columnDefinition = "TEXT")
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussionId")
    var discussion: Discussion? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdById")
    var createdBy: User? = null,
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replyToId")
    var replyTo: Message? = null
)