package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.auth.dto.request.SignUpTeacherRequest
import kr.flooding.backend.domain.auth.persistence.entity.VerifyCode
import kr.flooding.backend.domain.auth.persistence.repository.VerifyCodeRepository
import kr.flooding.backend.domain.role.enums.RoleType
import kr.flooding.backend.domain.user.persistence.entity.TeacherInfo
import kr.flooding.backend.domain.user.persistence.entity.User
import kr.flooding.backend.domain.user.persistence.repository.jpa.UserJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.thirdparty.email.EmailAdapter
import kr.flooding.backend.global.util.PasswordUtil
import kr.flooding.backend.global.util.RoleUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignUpTeacherUsecase(
	private val userJpaRepository: UserJpaRepository,
	private val verifyCodeRepository: VerifyCodeRepository,
	private val passwordEncoder: PasswordEncoder,
	private val emailAdapter: EmailAdapter,
	private val passwordUtil: PasswordUtil,
	private val roleUtil: RoleUtil,
) {
	@Transactional
	fun execute(request: SignUpTeacherRequest) {
		val encodedPassword = passwordEncoder.encode(request.password)

		if (userJpaRepository.existsByEmail(request.email)) {
			throw HttpException(ExceptionEnum.AUTH.DUPLICATED_EMAIL.toPair())
		}

		val teacherInfo = TeacherInfo(department = request.department)

		val user = userJpaRepository.save(
			User(
				email = request.email,
				encodedPassword = encodedPassword,
				teacherInfo = teacherInfo,
				emailVerifyStatus = false,
				gender = request.gender,
				name = request.name,
			)
		)
		user.setPendingUserState()
		roleUtil.saveRoles(user, listOf(RoleType.ROLE_USER, RoleType.ROLE_TEACHER))

		val userId = checkNotNull(user.id) { "User ID must not be null." }

		val randomVerifyCode = passwordUtil.generateRandomCode(6)
		emailAdapter.sendVerifyCode(request.email, randomVerifyCode)

		verifyCodeRepository.save(
			VerifyCode(
				id = userId,
				code = randomVerifyCode,
			)
		)
	}
}
