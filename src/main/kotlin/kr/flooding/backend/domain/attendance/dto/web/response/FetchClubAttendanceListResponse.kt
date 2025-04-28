package kr.flooding.backend.domain.attendance.dto.web.response

import kr.flooding.backend.domain.attendance.dto.common.response.FetchClubAttendanceResponse

class FetchClubAttendanceListResponse (
	val attendances: List<FetchClubAttendanceResponse>
)