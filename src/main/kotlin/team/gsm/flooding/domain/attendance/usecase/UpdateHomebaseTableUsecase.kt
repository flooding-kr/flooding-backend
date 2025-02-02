package team.gsm.flooding.domain.attendance.usecase

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import team.gsm.flooding.domain.attendance.dto.request.UpdateHomebaseTableRequest
import team.gsm.flooding.domain.attendance.entity.Attendance
import team.gsm.flooding.domain.attendance.repository.HomebaseGroupRepository
import team.gsm.flooding.domain.attendance.repository.AttendanceRepository
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import team.gsm.flooding.global.util.UserUtil
import java.time.LocalDate

@Service
@Transactional
class UpdateHomebaseTableUsecase (
    private val attendanceRepository: AttendanceRepository,
    private val homebaseGroupRepository: HomebaseGroupRepository,
    private val userRepository: UserRepository,
    private val userUtil: UserUtil
) {
	fun execute(request: UpdateHomebaseTableRequest){
		val nowDate = LocalDate.now()
		val currentUser = userUtil.getUser()

		val currentAttendance = attendanceRepository.findByAttendedAtAndStudent(nowDate, currentUser)
			.orElseThrow { ExpectedException(ExceptionEnum.NOT_FOUND_ATTENDANCE) }

		val newUsers = userRepository.findByIdIn(request.participants)
		val isExistReserve = attendanceRepository.existsByAttendedAtAndPeriodAndStudentIn(
			nowDate,
			currentAttendance.period,
			newUsers
		)
		if(isExistReserve){
			throw ExpectedException(ExceptionEnum.ALREADY_JOINED_ATTENDANCE)
		}

		val attendanceGroup = homebaseGroupRepository.findByAttendedAtAndProposer(nowDate, currentAttendance)
		attendanceRepository.deleteAll(attendanceGroup.participants)

		val newAttendances = newUsers.map {
			Attendance(
				classroom = currentAttendance.classroom,
				homebaseTable = currentAttendance.homebaseTable,
				student = it,
				period = currentAttendance.period,
			)
		}.toMutableList()

		val newAttendanceGroup = attendanceGroup.copy(
			participants = newAttendances
		)

		attendanceRepository.saveAll(newAttendances)
		homebaseGroupRepository.save(newAttendanceGroup)
	}
}