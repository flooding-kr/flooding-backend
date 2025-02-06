package team.gsm.flooding.domain.attendance.dto.response

import team.gsm.flooding.domain.attendance.entity.Attendance
import team.gsm.flooding.domain.attendance.entity.HomebaseGroup
import team.gsm.flooding.domain.classroom.entity.HomebaseTable
import team.gsm.flooding.domain.user.entity.Role
import team.gsm.flooding.domain.user.entity.StudentInfo
import team.gsm.flooding.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID


class FindReservedHomebaseTableResponse(
    val homebaseGroupId: UUID?,
    val homebaseTable: HomeBaseTableResponse,
    val period: Int,
    val participants: List<AttendanceResponse>,
    val proposer: AttendanceResponse,
    val reservedAt: LocalDate?,
) {
    companion object {
        fun toDto(homebaseGroup: HomebaseGroup): FindReservedHomebaseTableResponse {
            return FindReservedHomebaseTableResponse(
                homebaseGroupId = homebaseGroup.id,
                homebaseTable = HomeBaseTableResponse.toDto(homebaseGroup.homebaseTable),
                period = homebaseGroup.period,
                participants = homebaseGroup.participants.map { AttendanceResponse.toDto(it) },
                proposer = AttendanceResponse.toDto(homebaseGroup.proposer),
                reservedAt = homebaseGroup.attendedAt,
            )
        }
    }

    class HomeBaseTableResponse(
        val homebaseId: Long,
        val floor: Int,
        val name: String,
        val description: String,
        val teacher: UserResponse,
        val tableNumber: Int,
    ) {
        companion object {
            fun toDto(homebaseTable: HomebaseTable): HomeBaseTableResponse {
                return HomeBaseTableResponse(
                    homebaseId = homebaseTable.id,
                    floor = homebaseTable.homebase.floor,
                    name = homebaseTable.homebase.name,
                    description = homebaseTable.homebase.description,
                    teacher = UserResponse.toDto(homebaseTable.homebase.teacher),
                    tableNumber = homebaseTable.tableNumber,
                )
            }
        }
    }

    class UserResponse(
        val email: String,
        val studentInfo: StudentInfo,
        val name: String,
        val role: List<Role>,

        ) {
        companion object {
            fun toDto(user: User): UserResponse {
                return UserResponse(
                    email = user.email,
                    studentInfo = user.studentInfo,
                    name = user.name,
                    role = user.roles,
                )
            }
        }
    }

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
}
