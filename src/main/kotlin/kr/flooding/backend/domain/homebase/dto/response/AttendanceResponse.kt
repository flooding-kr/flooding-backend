package kr.flooding.backend.domain.homebase.dto.response

import kr.flooding.backend.domain.attendance.entity.Attendance
import kr.flooding.backend.global.util.StudentUtil

class AttendanceResponse(
	val name: String,
	val schoolNumber: String,
) {
	companion object {
		fun toDto(attendance: Attendance): kr.flooding.backend.domain.homebase.dto.response.AttendanceResponse =
			kr.flooding.backend.domain.homebase.dto.response.AttendanceResponse(
				name = attendance.student.name,
				schoolNumber =
					attendance.student.studentInfo.let {
						StudentUtil.calcStudentNumber(it.year, it.classroom, it.number)
					},
			)
	}
}
