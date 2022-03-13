package com.saliktariq.sendmailsendgrid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.mail.*
import javax.mail.internet.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val userName =  "apikey"
        val password =  "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"

        val emailFrom = "info@smartmanager.app"
        val emailTo = "saliktariq@icloud.com"
        val emailCC = "hyperamazing@gmail.com"

        val subject = "SMTP Test"
        val text = "Hello Kotlin Mail"

        val props = Properties()
        putIfMissing(props, "mail.smtp.host", "smtp.sendgrid.net")
        putIfMissing(props, "mail.smtp.port", "25")
        putIfMissing(props, "mail.smtp.auth", "true")
        putIfMissing(props, "mail.smtp.starttls.enable", "false")

        val session = Session.getDefaultInstance(props, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(userName, password)
            }
        })

        session.debug = true

        try {
            val mimeMessage = MimeMessage(session)
            mimeMessage.setFrom(InternetAddress(emailFrom))
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo, false))
            mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailCC, false))
            mimeMessage.setText(text)
            mimeMessage.subject = subject
            mimeMessage.sentDate = Date()

            val smtpTransport = session.getTransport("smtp")
            GlobalScope.launch {
                smtpTransport.connect()
                smtpTransport.sendMessage(mimeMessage, mimeMessage.allRecipients)
                smtpTransport.close()
            }

        } catch (messagingException: MessagingException) {
            messagingException.printStackTrace()
        }


    }}

private fun putIfMissing(props: Properties, key: String, value: String) {
    if (!props.containsKey(key)) {
        props[key] = value
    }
}