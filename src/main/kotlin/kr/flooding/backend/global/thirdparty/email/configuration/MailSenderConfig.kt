package kr.flooding.backend.global.thirdparty.email.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailSenderConfig {
	@Bean
	fun emailSender(): JavaMailSender = JavaMailSenderImpl()
}
