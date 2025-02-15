package team.gsm.flooding.global.util

import org.springframework.stereotype.Component
import java.util.Random

@Component
class PasswordUtil {
	fun generateSixRandomCode(): String {
		val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
		val random = Random()
		val code = StringBuilder()

		for (j in 0..5) {
			code.append(characters[random.nextInt(characters.length)])
		}

		return code.toString()
	}
}
