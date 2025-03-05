package kr.flooding.backend.global.util

import org.springframework.stereotype.Component
import java.util.Random

@Component
class PasswordUtil {
	fun generateRandomCode(length: Int): String {
		val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
		val random = Random()
		val code = StringBuilder()

		for (j in 0..<length) {
			code.append(characters[random.nextInt(characters.length)])
		}

		return code.toString()
	}
}
