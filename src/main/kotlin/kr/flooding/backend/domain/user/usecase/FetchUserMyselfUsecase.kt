package kr.flooding.backend.domain.user.usecase

import kr.flooding.backend.domain.role.persistence.repository.jpa.RoleJpaRepository
import kr.flooding.backend.domain.user.dto.common.response.TeacherInfoResponse
import kr.flooding.backend.domain.user.dto.web.response.FetchUserMyselfResponse
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchUserMyselfUsecase(
	private val userUtil: UserUtil,
	private val s3Adapter: S3Adapter,
	private val roleJpaRepository: RoleJpaRepository
) {
	fun execute(): FetchUserMyselfResponse {
		val user = userUtil.getUser()
		val roles = roleJpaRepository.findByUser(user)

		val teacherInfoResponse = user.teacherInfo?.let {
			TeacherInfoResponse(it.department)
		}

		val profileImage = user.profileImageKey?.let {
			s3Adapter.generatePresignedUrl(it)
		}

		return FetchUserMyselfResponse(
			id = requireNotNull(user.id),
			name = user.name,
			gender = user.gender,
			studentInfo = user.studentInfo?.toModel(),
			teacherInfo = teacherInfoResponse,
			email = user.email,
			profileImage = profileImage,
			roles = roles.map { it.type }
		)
	}
}
