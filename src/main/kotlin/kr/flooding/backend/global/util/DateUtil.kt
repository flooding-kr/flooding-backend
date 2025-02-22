package kr.flooding.backend.global.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

fun LocalDateTime.toDate(zone: ZoneId = ZoneId.systemDefault()): Date = Date.from(this.atZone(zone).toInstant())
