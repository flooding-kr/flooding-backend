package team.gsm.flooding.domain.homebase.usecase

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import team.gsm.flooding.domain.attendance.entity.Attendance
import team.gsm.flooding.domain.attendance.repository.AttendanceRepository
import team.gsm.flooding.domain.classroom.repository.HomebaseTableRepository
import team.gsm.flooding.domain.homebase.dto.request.ReserveHomebaseTableRequest
import team.gsm.flooding.domain.homebase.entity.HomebaseGroup
import team.gsm.flooding.domain.homebase.repository.HomebaseGroupRepository
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException
import team.gsm.flooding.global.util.UserUtil
import java.time.LocalDate

@Service
@Transactional
class ReserveHomebaseTableUsecase(
	private val attendanceRepository: AttendanceRepository,
	private val homebaseGroupRepository: HomebaseGroupRepository,
	private val homebaseTableRepository: HomebaseTableRepository,
	private val userRepository: UserRepository,
	private val userUtil: UserUtil,
) {
	fun execute(request: ReserveHomebaseTableRequest) {
		val currentUser = userUtil.getUser()
		val participants = userRepository.findByIdIn(request.participants)
		val nowDate = LocalDate.now()

		val homebaseTable =
			homebaseTableRepository
				.findByTableNumberAndHomebaseFloor(
					request.tableNumber,
					request.floor,
				).orElseThrow { HttpException(ExceptionEnum.NOT_FOUND_TABLE) }

		// 해당 자리의 사용 여부
		homebaseGroupRepository
			.existsByHomebaseTableAndPeriodAndAttendedAt(
				homebaseTable,
				request.period,
				nowDate,
			).takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.EXISTS_USED_TABLE)
			}

		// 이미 자리가 예약된 참여자 여부
		val allUsers = participants + currentUser
		attendanceRepository
			.existsByAttendedAtAndPeriodAndStudentIn(
				nowDate,
				request.period,
				allUsers,
			).takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.ALREADY_JOINED_ATTENDANCE)
			}

		val currentUserAttendance =
			Attendance(
				homebaseTable = homebaseTable,
				classroom = homebaseTable.homebase,
				period = request.period,
				student = currentUser,
			)

		val participantAttendances =
			participants
				.map {
					Attendance(
						homebaseTable = homebaseTable,
						classroom = homebaseTable.homebase,
						period = request.period,
						student = it,
					)
				}.toMutableList()
		val allAttendances = participantAttendances + currentUserAttendance

		val homebaseGroup =
			HomebaseGroup(
				homebaseTable = homebaseTable,
				period = request.period,
				participants = participantAttendances,
				proposer = currentUserAttendance,
			)

		attendanceRepository.saveAll(allAttendances)
		homebaseGroupRepository.save(homebaseGroup)
	}
}
