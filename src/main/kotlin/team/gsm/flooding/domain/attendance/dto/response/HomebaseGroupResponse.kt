package team.gsm.flooding.domain.attendance.dto.response

import team.gsm.flooding.domain.attendance.entity.HomebaseGroup
import java.time.LocalDate
import java.util.UUID


class HomebaseGroupResponse(
    val homebaseGroupId: UUID?,
    val homebaseTable: HomebaseTableResponse,
    val period: Int,
    val participants: List<AttendanceResponse>,
    val proposer: AttendanceResponse,
    val reservedAt: LocalDate?,
) {
    companion object {
        fun toDto(homebaseGroup: HomebaseGroup): HomebaseGroupResponse {
            return HomebaseGroupResponse(
                homebaseGroupId = homebaseGroup.id,
                homebaseTable = HomebaseTableResponse.toDto(homebaseGroup.homebaseTable),
                period = homebaseGroup.period,
                participants = homebaseGroup.participants.map { AttendanceResponse.toDto(it) },
                proposer = AttendanceResponse.toDto(homebaseGroup.proposer),
                reservedAt = homebaseGroup.attendedAt,
            )
        }
    }
}
