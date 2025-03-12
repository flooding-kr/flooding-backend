package kr.flooding.backend.global.thirdparty.email.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailSenderConfig(
	@Value("\${spring.mail.host}")
	private val mailHost: String,
	@Value("\${spring.mail.username}")
	private val mailUsername: String,
	@Value("\${spring.mail.password}")
	private val mailPassword: String,
) {
	companion object {
		private val TRANSPORT_PROTOCOL = "mail.transport.protocol"
		private val SMTP_AUTH = "mail.smtp.auth"
		private val SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required"
		private val SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable"
	}

	@Bean
	fun emailSender(): JavaMailSender =
		JavaMailSenderImpl().apply {
			host = mailHost
			username = mailUsername
			password = mailPassword
			port = 587
			javaMailProperties[TRANSPORT_PROTOCOL] = "smtp"
			javaMailProperties[SMTP_AUTH] = "true"
			javaMailProperties[SMTP_STARTTLS_REQUIRED] = "true"
			javaMailProperties[SMTP_STARTTLS_ENABLE] = "true"
		}
}
