package com.ola.mlcontestapi.common.services

import com.cloudinary.Cloudinary
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.Timestamp
import java.util.*


@Service
class FileUploadService {
    @Value("\${cloudinary_url}")
    private val cloudinaryUrl: String? = null

    @Value("\${file.upload-dir}")
    private val uploadFolder: String? = null

    fun uploadImage(file: MultipartFile): Pair<String, String> {
        val cloudinary = Cloudinary(cloudinaryUrl)
        cloudinary.config.secure = true
        val uploadResult = cloudinary.uploader().upload(file.bytes, mapOf("unique_filename" to true))
        val url = uploadResult["url"] as String
        val publicId = uploadResult["public_id"] as String
        return Pair(url, publicId)
    }

    fun uploadDataset(file: MultipartFile): Pair<String, Long> {
        val bytes = file.bytes
        val date = Date()
        val timestamp = Timestamp(date.time)
        val name = "${timestamp.time}${file.originalFilename}"
        val path = Paths.get("$uploadFolder$name")
        Files.write(path, bytes)
        return Pair(name, bytes.size.toLong())
    }

    fun deleteImage(id: String) {
        val cloudinary = Cloudinary(cloudinaryUrl)
        cloudinary.config.secure = true
        cloudinary.uploader().destroy(id, emptyMap<String, String>())
    }
}