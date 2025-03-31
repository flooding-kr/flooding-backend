package kr.flooding.backend.domain.club.usecase

import kr.flooding.backend.domain.attendance.entity.Attendance
import kr.flooding.backend.domain.attendance.repository.AttendanceRepository
import kr.flooding.backend.domain.club.dto.request.AttendClubLeaderRequest
import kr.flooding.backend.domain.club.entity.ClubStatus
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.repository.ClubMemberRepository
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class AttendClubLeaderUsecase(
	private val userUtil: UserUtil,
	private val clubRepository: ClubRepository,
	private val attendanceRepository: AttendanceRepository,
	private val userRepository: UserRepository,
	private val clubMemberRepository: ClubMemberRepository,
) {
	fun execute(request: AttendClubLeaderRequest) {
		val currentUser = userUtil.getUser()
		val nowDate = LocalDate.now()
		val club =
			clubRepository.findById(request.clubId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}
		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.CLUB.NOT_APPROVED_CLUB.toPair())
		}

		if (currentUser != club.leader) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_LEADER.toPair())
		}

		val clubMembers = clubMemberRepository.findByClubId(request.clubId)
		val clubMemberIds = clubMembers.map { it.user.id }.toSet()

		val nonClubMembers = request.studentIds.filterNot { it in clubMemberIds }
		if (nonClubMembers.isNotEmpty()) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_MEMBER.toPair())
		}

		val students = userRepository.findAllById(request.studentIds)

		val existingAttendAnce =
			attendanceRepository
				.findByClubAndPeriodAnAndAttendedAt(
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
