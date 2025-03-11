package kr.flooding.backend.global.thirdparty.mail

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.Properties

@Configuration
class MailConfig(
	@Value("\${spring.mail.host}")
	private val host: String,
	@Value("\${spring.mail.username}")
	private val username: String,
	@Value("\${spring.mail.password}")
	private val password: String,
) {
	@Bean
	fun mailSender(): JavaMailSender {
		val javaMailSender = JavaMailSenderImpl()
		javaMailSender.host = host
		javaMailSender.username = username
		javaMailSender.password = password

		javaMailSender.port = 587
		javaMailSender.javaMailProperties = getMailProperties()

		return javaMailSender
	}

	private fun getMailProperties(): Properties {
		val properties = Properties()
		properties.setProperty("mail.transport.protocol", "smtp")
		properties.setProperty("mail.smtp.auth", "true")
		properties.setProperty("mail.smtp.starttls.enable", "true")
		properties.setProperty("mail.debug", "true")
		properties.setProperty("mail.smtp.ssl.trust", host)
		properties.setProperty("mail.smtp.ssl.enable", "true")
		return properties
	}
}
