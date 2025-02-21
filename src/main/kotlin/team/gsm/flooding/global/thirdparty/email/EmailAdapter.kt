package team.gsm.flooding.global.thirdparty.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException

@Component
class EmailAdapter(
	private val mailSender: JavaMailSender,
	@Value("\${spring.mail.verify-url}")
	private val verifyUrl: String,
) {
	@Async
	fun sendVerifyCode(
		email: String,
		verifyCode: String,
	) {
		val message = mailSender.createMimeMessage()
		val helper = MimeMessageHelper(message, "utf-8")
		helper.setTo(email)
		helper.setSubject("NoName 이메일 인증")
		helper.setText(
			"""
			<h1>$verifyCode</h1>
			<a href="${"$verifyUrl?code=$verifyCode&email=$email"}">${"$verifyUrl?code=$verifyCode&email=$email"}</a>
			""".trimIndent(),
			true,
		)

		runCatching {
			mailSender.send(message)
		}.onFailure {
			throw HttpException(ExceptionEnum.UNKNOWN_ERROR_EMAIL)
		}
	}
}
