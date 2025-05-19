package kr.flooding.backend.global.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class DateUtil {
	companion object {
		fun LocalDateTime.toDate(zone: ZoneId = ZoneId.systemDefault()): Date = Date.from(this.atZone(zone).toInstant())

		// Following LocalDate's atStartOfDay() naming
		fun LocalDate.atEndOfDay(): LocalDateTime = this.atStartOfDay().plusDays(1)
	}
}