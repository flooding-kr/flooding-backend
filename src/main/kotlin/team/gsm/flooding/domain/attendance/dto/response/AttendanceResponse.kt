package team.gsm.flooding.domain.attendance.dto.response

import team.gsm.flooding.domain.attendance.entity.Attendance
import java.time.LocalDate
import java.util.*

class AttendanceResponse(
    val attendanceId: UUID?,
    val student: UserResponse,
    val period: Int,
    val attendedAt: LocalDate?,
    ) {
        companion object {
            fun toDto(attendance: Attendance): AttendanceResponse {
                return AttendanceResponse(
                    attendanceId = attendance.id,
                    student = UserResponse.toDto(attendance.student),
                    period = attendance.period,
                    attendedAt = attendance.attendedAt,
                )
            }
        }
    }