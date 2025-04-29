package kr.flooding.backend.domain.user.usecase.admin

import kr.flooding.backend.domain.user.dto.common.response.StudentInfoResponse
import kr.flooding.backend.domain.user.dto.web.response.FetchPendingUserListResponse
import kr.flooding.backend.domain.user.dto.common.response.PendingUserResponse
import kr.flooding.backend.domain.user.dto.common.response.TeacherInfoResponse
import kr.flooding.backend.domain.user.enums.UserState
import kr.flooding.backend.domain.user.persistence.repository.jpa.UserJpaRepository
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchPendingUserListUsecase(
	private val userJpaRepository: UserJpaRepository,
	private val s3Adapter: S3Adapter,
) {
	fun execute(): FetchPendingUserListResponse {
		val pendingUsers = userJpaRepository.findByState(UserState.PENDING)
		return FetchPendingUserListResponse(
			pendingUsers.map {
				val profileImageUrl = it.profileImageKey?.let { key ->
					s3Adapter.generatePresignedUrl(key)
				}

				val studentInfoResponse = it.studentInfo?.let {
					StudentInfoResponse.toDto(it)
				}

				val teacherInfoResponse = it.teacherInfo?.let {
					TeacherInfoResponse(it.department)
				}

				PendingUserResponse(
					id = requireNotNull(it.id),
					email = it.email,
					name = it.name,
					profileImageUrl = profileImageUrl,
					studentInfoResponse = studentInfoResponse,
					teacherInfoResponse = teacherInfoResponse,
				)
			}
		)
	}
}
