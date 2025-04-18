package kr.flooding.backend.global.thirdparty.email

import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class EmailAdapter(
	private val mailSender: JavaMailSender,
	@Value("\${client.verify-url}")
	private val verifyUrl: String,
	@Value("\${client.reset-password-url}")
	private val resetPasswordUrl: String,
	@Value("\${client.invite-club-url}")
	private val inviteClubUrl: String,
) {
	fun sendInviteClub(
		email: String,
		inviteCode: String,
		clubName: String,
	) {
		this.sendEmail(
			email = email,
			subject = "플러딩 | 동아리 초대 확인",
			text =
				"""
				<h1>$clubName 동아리에서 초대했습니다. 아래의 링크를 통해서 초대를 수락해주세요.</h1>
				<a href="$inviteClubUrl?code=$inviteCode">[수락하기]</a>
				""".trimIndent(),
		)
	}

	fun sendVerifyCode(
		email: String,
		verifyCode: String,
	) {
		this.sendEmail(
			email = email,
			subject = "플러딩 이메일 인증",
			text =
				"""
				<h1>아래의 링크에서 이메일을 인증해주세요.</h1>
				<a href="${"$verifyUrl?code=$verifyCode&email=$email"}">[인증하기]</a>
				""".trimIndent(),
		)
	}

	fun sendResetPassword(
		email: String,
		code: String,
	) {
		this.sendEmail(
			email = email,
			subject = "플러딩 비밀번호 찾기 요청",
			text =
				"""
				<h1>아래의 링크에서 비밀번호를 변경해주세요.</h1>
				<a href="${"$resetPasswordUrl?code=$code&email=$email"}">[비밀번호 변경하기]</a>
				""".trimIndent(),
		)
	}

	@Async
	fun sendEmail(
		email: String,
		subject: String,
		text: String,
	) {
		val message = mailSender.createMimeMessage()
		val helper = MimeMessageHelper(message, "utf-8")
		helper.setTo(email)
		helper.setSubject(subject)
		helper.setText(text, true)

		runCatching {
			mailSender.send(message)
		}.onFailure {
			throw HttpException(ExceptionEnum.AUTH.UNKNOWN_ERROR_EMAIL.toPair())
		}
	}
}
