package bj.ifri.mlcontestapi.common.services

import com.cloudinary.Cloudinary
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class FileUploadService {
    @Value("\${cloudinary_url}")
    private val cloudinaryUrl: String? = null

    fun uploadImage(file: MultipartFile): Pair<String, String> {
        val cloudinary = Cloudinary(cloudinaryUrl)
        cloudinary.config.secure = true
        val uploadResult = cloudinary.uploader().upload(file.bytes, mapOf("unique_filename" to true))
        val url = uploadResult["url"] as String
        val publicId = uploadResult["public_id"] as String
        return Pair(url, publicId)
    }

    fun deleteImage(id: String) {
        val cloudinary = Cloudinary(cloudinaryUrl)
        cloudinary.config.secure = true
        cloudinary.uploader().destroy(id, emptyMap<String, String>())
    }
}