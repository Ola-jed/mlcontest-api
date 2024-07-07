package bj.ifri.mlcontestapi.modules.models.entities

import jakarta.persistence.*

@Entity
@Table(name = "app_column_description")
class ColumnDescription(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var name: String,
    var description: String,
    var dependant: Boolean,
    var note: String? = null
)