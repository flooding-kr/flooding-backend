package kr.flooding.backend.global.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class DateUtil {
	companion object {
		fun LocalDateTime.toDate(zone: ZoneId = ZoneId.systemDefault()): Date = Date.from(this.atZone(zone).toInstant())
		fun LocalDateTime.getAtStartOfDay(): LocalDateTime = this.toLocalDate().atStartOfDay()
		fun LocalDateTime.getAtEndOfDay(): LocalDateTime = this.toLocalDate().atStartOfDay().plusDays(1)

		fun LocalDate.getAtStartOfDay(): LocalDateTime = this.atStartOfDay()
		fun LocalDate.getAtEndOfDay(): LocalDateTime = this.atStartOfDay().plusDays(1)
	}
}