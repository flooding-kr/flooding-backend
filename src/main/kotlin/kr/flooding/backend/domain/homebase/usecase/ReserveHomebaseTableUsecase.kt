package kr.flooding.backend.domain.homebase.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.attendance.entity.Attendance
import kr.flooding.backend.domain.attendance.repository.AttendanceRepository
import kr.flooding.backend.domain.homebase.dto.request.ReserveHomebaseTableRequest
import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import kr.flooding.backend.domain.homebase.repository.jpa.HomebaseGroupRepository
import kr.flooding.backend.domain.homebaseParticipants.entity.HomebaseParticipant
import kr.flooding.backend.domain.homebaseParticipants.repository.jpa.HomebaseParticipantRepository
import kr.flooding.backend.domain.homebaseTable.repository.jdsl.HomebaseTableJdslRepository
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@Transactional
class ReserveHomebaseTableUsecase(
	private val attendanceRepository: AttendanceRepository,
	private val homebaseGroupRepository: HomebaseGroupRepository,
	private val homebaseTableJdslRepository: HomebaseTableJdslRepository,
	private val homebaseParticipantRepository: HomebaseParticipantRepository,
	private val userRepository: UserRepository,
	private val userUtil: UserUtil,
) {
	fun execute(request: ReserveHomebaseTableRequest) {
		val currentUser = userUtil.getUser()
		val participants = userRepository.findByIdIn(request.participants)
		val nowDate = LocalDate.now()

		request.participants
			.takeIf { it.contains(currentUser.id) }
			?.let {
				throw HttpException(ExceptionEnum.CLASSROOM.PROPOSER_CANNOT_BE_PARTICIPANT.toPair())
			}

		val homebaseTable =
			homebaseTableJdslRepository
				.findWithHomebaseByTableNumberAndFloor(
					request.tableNumber,
					request.floor,
				).orElseThrow { HttpException(ExceptionEnum.CLASSROOM.NOT_FOUND_TABLE.toPair()) }

		if (request.participants.size + 1 > homebaseTable.maxSeats) {
			throw HttpException(ExceptionEnum.HOMEBASE.MAX_CAPACITY_EXCEEDED.toPair())
		}

		// 해당 자리의 사용 여부
		homebaseGroupRepository
			.existsByHomebaseTableAndPeriodAndAttendedAt(
				homebaseTable,
				request.period,
				nowDate,
			).takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.CLASSROOM.EXISTS_USED_TABLE.toPair())
			}

		// 이미 자리가 예약된 참여자 여부
		val allUsers = participants + currentUser
		homebaseParticipantRepository
			.existsByHomebaseGroupAttendedAtAndHomebaseGroupPeriodAndHomebaseGroupProposerInAndUserIn(
				nowDate,
				request.period,
				allUsers,
				allUsers,
			).takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.CLASSROOM.ALREADY_JOINED_ATTENDANCE.toPair())
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
				proposer = currentUser,
			)

		val homebaseParticipants =
			participants.map {
				HomebaseParticipant(
					user = it,
					homebaseGroup = homebaseGroup,
				)
			}

		homebaseParticipantRepository.saveAll(homebaseParticipants)
		attendanceRepository.saveAll(allAttendances)
		homebaseGroupRepository.save(homebaseGroup)
	}
}
