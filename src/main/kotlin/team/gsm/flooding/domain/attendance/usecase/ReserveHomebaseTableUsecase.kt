package team.gsm.flooding.domain.attendance.usecase

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import team.gsm.flooding.domain.attendance.dto.request.ReserveHomebaseTableRequest
import team.gsm.flooding.domain.attendance.entity.Attendance
import team.gsm.flooding.domain.attendance.entity.AttendanceGroup
import team.gsm.flooding.domain.attendance.repository.AttendanceGroupRepository
import team.gsm.flooding.domain.attendance.repository.AttendanceRepository
import team.gsm.flooding.domain.classroom.repository.HomebaseTableRepository
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import team.gsm.flooding.global.util.UserUtil
import java.time.LocalDate

@Service
@Transactional
class ReserveHomebaseTableUsecase (
	private val attendanceRepository: AttendanceRepository,
	private val attendanceGroupRepository: AttendanceGroupRepository,
	private val homebaseTableRepository: HomebaseTableRepository,
	private val userRepository: UserRepository,
	private val userUtil: UserUtil
) {
	fun execute(request: ReserveHomebaseTableRequest){
		val currentUser = userUtil.getUser()
		val participants = userRepository.findByIdIn(request.participants)
		val allUsers = participants + currentUser
		val nowDate = LocalDate.now()

		val homebaseTable = homebaseTableRepository.findByTableNumberAndHomebaseFloor(
			request.tableNumber,
			request.floor
		).orElseThrow { ExpectedException(ExceptionEnum.NOT_FOUND_HOMEBASE_TABLE) }

		val isExistReserve = attendanceRepository.existsByAttendedAtAndPeriodAndStudentIn(
			nowDate,
			request.period,
			allUsers
		)
		if(isExistReserve){
			throw ExpectedException(ExceptionEnum.ALREADY_JOINED_ATTENDANCE)
		}

		val currentUserAttendance = Attendance(
			homebaseTable = homebaseTable,
			period = request.period,
			student = currentUser,
		)

		val participantAttendances = participants.map {
			Attendance(
				homebaseTable = homebaseTable,
				period = request.period,
				student = it
			)
		}.toMutableList()
		val allAttendances = participantAttendances + currentUserAttendance

		val attendanceGroup = AttendanceGroup(
			homebaseTable = homebaseTable,
			period = request.period,
			participants = participantAttendances,
			proposer = currentUserAttendance,
		)

		attendanceRepository.saveAll(allAttendances)
		attendanceGroupRepository.save(attendanceGroup)
	}
}