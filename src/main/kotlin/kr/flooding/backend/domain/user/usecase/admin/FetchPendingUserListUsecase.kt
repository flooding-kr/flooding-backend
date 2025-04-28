package kr.flooding.backend.domain.user.usecase.admin

import kr.flooding.backend.domain.user.dto.common.response.StudentInfoResponse
import kr.flooding.backend.domain.user.dto.web.response.FetchPendingUserListResponse
import kr.flooding.backend.domain.user.dto.web.response.FetchUserInfoResponse
import kr.flooding.backend.domain.user.enums.UserState
import kr.flooding.backend.domain.user.persistence.repository.jpa.UserJpaRepository
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

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

				FetchUserInfoResponse(
					id = requireNotNull(it.id),
					email = it.email,
					name = it.name,
					gender = it.gender,
					studentInfo = it.studentInfo?.let { studentInfo -> StudentInfoResponse.toDto(studentInfo) },
					profileImageUrl = profileImageUrl,
				)
			}
		)
	}
}
