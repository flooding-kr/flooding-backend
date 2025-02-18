package team.gsm.flooding.domain.homebase.dto.response

import team.gsm.flooding.domain.attendance.entity.Attendance
import team.gsm.flooding.global.util.StudentUtil

class AttendanceResponse(
	val name: String,
	val schoolNumber: String,
) {
	companion object {
		fun toDto(attendance: Attendance): AttendanceResponse =
			AttendanceResponse(
				name = attendance.student.name,
				schoolNumber =
					attendance.student.studentInfo.let {
						StudentUtil.calcStudentNumber(it.year, it.classroom, it.number)
					},
			)
	}
}
