package com.example.captainsclub2.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import java.util.Properties
//import javax.mail.*
import javax.mail.Session
import javax.mail.Transport
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import javax.mail.Message
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object EmailUtils {
    private const val EMAIL = "your-email@gmail.com" // Замените на ваш email
    private const val PASSWORD = "your-password" // Замените на пароль от email

    fun sendEmail(context: Context, recipient: String, subject: String, body: String) {
        if (!isNetworkAvailable(context)) {
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_SHORT).show()
            return
        }

        Thread {
            try {
                val props = Properties().apply {
                    put("mail.smtp.host", "smtp.gmail.com")
                    put("mail.smtp.socketFactory.port", "465")
                    put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
                    put("mail.smtp.auth", "true")
                    put("mail.smtp.port", "465")
                }

                val session = Session.getDefaultInstance(props, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(EMAIL, PASSWORD)
                    }
                })

                val message = MimeMessage(session).apply {
                    setFrom(InternetAddress(EMAIL))
                    addRecipient(Message.RecipientType.TO, InternetAddress(recipient))
                    this.subject = subject
                    setText(body)
                }

                Transport.send(message)
                Toast.makeText(context, "Отчет отправлен", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Ошибка отправки email", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}