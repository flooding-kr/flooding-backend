package kr.flooding.backend.global.util

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
abstract class TodayUtil {
	companion object {
		fun getAtStartOfToday(): LocalDateTime = LocalDate.now().atStartOfDay()

		fun getAtEndOfToday(): LocalDateTime = LocalDate.now().atStartOfDay().plusDays(1)
	}
}
