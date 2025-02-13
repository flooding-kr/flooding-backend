package team.gsm.flooding.domain.attendance.dto.response

import team.gsm.flooding.domain.attendance.entity.HomebaseGroup
import team.gsm.flooding.domain.classroom.entity.HomebaseTable
import java.time.LocalDate


class FindReservedHomebaseResponse(
    val homebaseTableId: Long,
    val floor: Int,
    val tableNumber: Int,
    val attendedAt: LocalDate?,
    val isAttended: Boolean,
    val participants: List<AttendanceResponse>,
) {
    companion object {
        fun toDto(homebaseTable: HomebaseTable, homebaseGroup: HomebaseGroup?): FindReservedHomebaseResponse {
            return FindReservedHomebaseResponse(
                homebaseTableId = homebaseTable.id,
                floor = homebaseTable.homebase.floor,
                tableNumber = homebaseTable.tableNumber,
                attendedAt = homebaseGroup?.attendedAt,
                isAttended = homebaseGroup != null,
                participants = homebaseGroup?.participants.orEmpty().map { AttendanceResponse.toDto(it) },
            )
        }
    }
}
