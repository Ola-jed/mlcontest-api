package bj.ifri.mlcontestapi.common.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.sql.Timestamp


@Service
class FileUploadService {
    @Value("\${file.upload-dir}")
    private val uploadFolder: String? = null

    fun uploadFile(file: MultipartFile): String {
        val timestamp = Timestamp(Date().time)
        val name = "${timestamp.time}${file.originalFilename}"
        val path = Paths.get(uploadFolder.toString() + name)
        Files.write(path, file.bytes)
        return name
    }
}