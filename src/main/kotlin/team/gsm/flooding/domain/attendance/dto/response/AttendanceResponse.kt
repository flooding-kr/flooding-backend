package team.gsm.flooding.domain.attendance.dto.response

import team.gsm.flooding.domain.attendance.entity.Attendance
import team.gsm.flooding.global.util.StudentUtil
import team.gsm.flooding.global.util.UserUtil

class AttendanceResponse(
    val name: String,
    val schoolNumber: String,
    ) {
        companion object {
            fun toDto(attendance: Attendance): AttendanceResponse {
                return AttendanceResponse(
                    name = attendance.student.name,
                    schoolNumber = attendance.student.studentInfo.let {
                        StudentUtil.calcStudentNumber(it.year, it.classroom, it.number)
                    }
                )
            }
        }
    }