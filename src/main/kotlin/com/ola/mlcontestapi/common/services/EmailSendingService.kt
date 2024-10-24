package com.ola.mlcontestapi.common.services

import com.ola.mlcontestapi.modules.auth.entities.PasswordReset
import org.springframework.core.io.ResourceLoader
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.util.FileCopyUtils

@Service
class EmailSendingService(private val mailSender: JavaMailSender, private val resourceLoader: ResourceLoader) {
    fun sendPasswordResetMail(passwordReset: PasswordReset, user: com.ola.mlcontestapi.modules.user.entities.User) {
        val forgotPasswordTxt = resourceLoader.getResource("classpath:mail/text/forgot-password.txt")
        val forgotPasswordTxtBytes = FileCopyUtils.copyToByteArray(forgotPasswordTxt.inputStream)
        val forgotPasswordTxtStr = String(forgotPasswordTxtBytes).replace("{code}", passwordReset.code)

        val forgotPasswordHtml = resourceLoader.getResource("classpath:mail/html/forgot-password.html")
        val forgotPasswordHtmlBytes = FileCopyUtils.copyToByteArray(forgotPasswordHtml.inputStream)
        val forgotPasswordHtmlStr = String(forgotPasswordHtmlBytes).replace("{code}", passwordReset.code)

        sendMail(user.email, "MLContest : Password reset", forgotPasswordHtmlStr, forgotPasswordTxtStr)
    }

    private fun sendMail(to: String, subject: String, htmlContent: String, textContent: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(to)
        helper.setFrom("MLContest")
        helper.setSubject(subject)
        helper.setText(textContent, htmlContent)
        mailSender.send(message)
    }
}