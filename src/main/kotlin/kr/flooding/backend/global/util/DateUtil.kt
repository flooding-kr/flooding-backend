package kr.flooding.backend.global.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class DateUtil {
	companion object {
		fun LocalDateTime.toDate(zone: ZoneId = ZoneId.systemDefault()): Date = Date.from(this.atZone(zone).toInstant())
		fun getAtStartOfToday(): LocalDateTime = LocalDate.now().atStartOfDay()
		fun getAtEndOfToday(): LocalDateTime = LocalDate.now().atStartOfDay().plusDays(1)
	}
}