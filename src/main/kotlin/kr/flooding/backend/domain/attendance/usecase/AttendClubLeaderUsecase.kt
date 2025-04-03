package kr.flooding.backend.domain.attendance.usecase

import kr.flooding.backend.domain.attendance.entity.Attendance
import kr.flooding.backend.domain.attendance.repository.AttendanceRepository
import kr.flooding.backend.domain.club.dto.request.AttendClubLeaderRequest
import kr.flooding.backend.domain.club.entity.ClubStatus
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.repository.jpa.ClubMemberJpaRepository
import kr.flooding.backend.domain.period.repository.PeriodRepository
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime

@Service
@Transactional
class AttendClubLeaderUsecase(
	private val userUtil: UserUtil,
	private val clubRepository: ClubRepository,
	private val attendanceRepository: AttendanceRepository,
	private val userRepository: UserRepository,
	private val clubMemberJpaRepository: ClubMemberJpaRepository,
	private val periodRepository: PeriodRepository,
) {
	fun execute(request: AttendClubLeaderRequest) {
		val club =
			clubRepository.findById(request.clubId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}
		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.CLUB.NOT_APPROVED_CLUB.toPair())
		}

		val currentUser = userUtil.getUser()

		if (currentUser != club.leader) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_LEADER.toPair())
		}

		val nowDate = LocalDate.now()
		val nowTime = LocalTime.now()
		val period =
			periodRepository.findById(request.period).orElseThrow {
				HttpException(ExceptionEnum.ATTENDANCE.NOT_FOUND_PERIOD_INFO.toPair())
			}

		if (nowTime.isBefore(period.startTime) || nowTime.isAfter(period.endTime)) {
			throw HttpException(ExceptionEnum.ATTENDANCE.ATTENDANCE_OUT_OF_TIME_RANGE.toPair())
		}

		val clubMembers = clubMemberJpaRepository.findByClubId(request.clubId)
		val clubMemberIds = clubMembers.map { it.user.id }.toSet()

		val nonClubMembers = request.studentIds.filterNot { it in clubMemberIds }
		if (nonClubMembers.isNotEmpty()) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_MEMBER.toPair())
		}

		val students = userRepository.findAllById(request.studentIds)

		val existingAttendAnce =
			attendanceRepository
				.findByClubAndPeriodAndAttendedAt(
					club,
					request.period,
					nowDate,
				).associateBy {
					it.student.id
				}

		val attendance =
			students.map { student ->
				existingAttendAnce[student.id]?.copy(isPresent = true, reason = null)
					?: Attendance(
						student = student,
						period = request.period,
						club = club,
						attendedAt = nowDate,
						isPresent = true,
						reason = null,
					)
			}

		attendanceRepository.saveAll(attendance)
	}
}
