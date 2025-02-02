package team.gsm.flooding.domain.attendance.usecase

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import team.gsm.flooding.domain.attendance.repository.HomebaseGroupRepository
import team.gsm.flooding.domain.attendance.repository.AttendanceRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import team.gsm.flooding.global.util.UserUtil
import java.time.LocalDate

@Service
@Transactional
class CancelHomebaseTableUsecase (
    private val attendanceRepository: AttendanceRepository,
    private val homebaseGroupRepository: HomebaseGroupRepository,
    private val userUtil: UserUtil
) {
	fun execute(){
		val nowDate = LocalDate.now()
		val currentUser = userUtil.getUser()

		val currentAttendance = attendanceRepository.findByAttendedAtAndStudent(nowDate, currentUser)
			.orElseThrow { ExpectedException(ExceptionEnum.NOT_FOUND_ATTENDANCE) }

		val attendanceGroup = homebaseGroupRepository.findByAttendedAtAndProposer(
			nowDate,
			currentAttendance
		)
		homebaseGroupRepository.delete(attendanceGroup)
	}
}