package kr.flooding.backend.global.thirdparty.email.configuration

import kr.flooding.backend.global.properties.MailProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailSenderConfig(
	private val mailProperties: MailProperties,
) {
	companion object {
		private val TRANSPORT_PROTOCOL = "mail.transport.protocol"
		private val SMTP_AUTH = "mail.smtp.auth"
		private val SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required"
		private val SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable"
		private val SMTP_PORT = 587
	}

	@Bean
	fun emailSender(): JavaMailSender =
		JavaMailSenderImpl().apply {
			host = mailProperties.host
			username = mailProperties.username
			password = mailProperties.password
			port = SMTP_PORT
			javaMailProperties[TRANSPORT_PROTOCOL] = "smtp"
			javaMailProperties[SMTP_AUTH] = "true"
			javaMailProperties[SMTP_STARTTLS_REQUIRED] = "true"
			javaMailProperties[SMTP_STARTTLS_ENABLE] = "true"
		}
}
