package kr.flooding.backend.domain.attendance.usecase

import kr.flooding.backend.domain.attendance.dto.common.response.FetchClubAttendanceResponse
import kr.flooding.backend.domain.attendance.dto.web.request.FetchClubAttendanceRequest
import kr.flooding.backend.domain.attendance.dto.web.response.FetchClubAttendanceListResponse
import kr.flooding.backend.domain.attendance.enums.AttendanceStatus
import kr.flooding.backend.domain.attendance.persistence.entity.Attendance
import kr.flooding.backend.domain.attendance.persistence.repository.jdsl.AttendanceJdslRepository
import kr.flooding.backend.domain.clubMember.persistence.repository.jdsl.ClubMemberJdslRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import kr.flooding.backend.global.util.FileUtil
import kr.flooding.backend.global.util.StudentUtil
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchClubAttendanceUsecase(
	private val attendanceJdslRepository: AttendanceJdslRepository,
	private val clubMemberJdslRepository: ClubMemberJdslRepository,
	private val userUtil: UserUtil,
	private val s3Adapter: S3Adapter
) {
	fun execute(request: FetchClubAttendanceRequest
	): FetchClubAttendanceListResponse {
		val currentUser = userUtil.getUser()
		val clubMembers = clubMemberJdslRepository.findWithUserAndClubByClubId(request.clubId)

		val club = clubMembers.firstOrNull()?.club ?:
			throw HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())

		if(currentUser.id != club.leader.id && currentUser.id != club.teacher?.id) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_LEADER.toPair())
		}

		val statusOrder = mapOf(
			AttendanceStatus.UNMARKED to 0,
			AttendanceStatus.ABSENT to 1,
			AttendanceStatus.PRESENT to 2
		)

		val attendancesByStudentId = attendanceJdslRepository.findWithStudentAndClubByAttendedAtAndPeriodAndClubId(
			attendedAt = request.date,
			period = request.period,
			clubId = request.clubId
		).associateBy { it.student.id }

		val responses = clubMembers.map { clubMember ->
			val user = clubMember.user
			val attendance = attendancesByStudentId[user.id]
			val studentInfo = requireNotNull(user.studentInfo)
			val year = requireNotNull(studentInfo.year)
			val profileImage = user.profileImageKey?.let {
				s3Adapter.generatePresignedUrl(it)
			}

			FetchClubAttendanceResponse(
				userId = requireNotNull(clubMember.user.id),
				name = clubMember.user.name,
				profileImage = profileImage,
				status = getAttendanceStatus(attendance),
				classroom = requireNotNull(studentInfo.classroom),
				number = requireNotNull(studentInfo.number),
				grade = StudentUtil.calcYearToGrade(year),
			)
		}.filter {
			request.status == null || it.status == request.status
		}.sortedBy {
			statusOrder[it.status]
		}

		return FetchClubAttendanceListResponse(responses)
	}

	fun getAttendanceStatus(attendance: Attendance?) = when {
		attendance == null -> AttendanceStatus.UNMARKED
		attendance.isPresent -> AttendanceStatus.PRESENT
		!attendance.isPresent -> AttendanceStatus.ABSENT
		else -> AttendanceStatus.UNMARKED
	}
}