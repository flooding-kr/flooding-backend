package team.gsm.flooding.global.thirdparty.email

import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class EmailAdapter(
	private val mailSender: JavaMailSender,
) {
	@Async
	fun sendVerifyCode(email: String, verifyCode: String) {
		val message = mailSender.createMimeMessage()
		val helper = MimeMessageHelper(message, "utf-8")
		helper.setTo(email)
		helper.setSubject("NoName 이메일 인증")
		helper.setText("""
		<h1>${verifyCode}</h1>
		<a href="${"http://localhost:8080/auth/verify?code=$verifyCode&email=$email"}">${"http://localhost:8080/auth/verify?code=$verifyCode&email=$email"}</a>
		""".trimIndent(), true)

		runCatching {
			mailSender.send(message)
		}.onFailure {
			throw ExpectedException(ExceptionEnum.UNKNOWN_ERROR_EMAIL)
		}
	}
}