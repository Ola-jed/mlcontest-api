package bj.ifri.mlcontestapi.modules.models.entities

import jakarta.persistence.*

@Entity
@Table(name = "app_dataset")
class Dataset(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    var fileType: FileType,
    var fileSize: Long,
    var fileUrl: String,
    var fileId: String,
    var itemCount: Int,
    var training: Boolean
) {
    enum class FileType {
        CSV,
        TSV,
        SPACE_DELIMITED_FILE,
        SQLite
    }
}