package kr.flooding.backend.domain.user.usecase.common

import kr.flooding.backend.domain.user.dto.web.response.FetchUserInfoResponse
import kr.flooding.backend.domain.user.dto.common.response.StudentInfoResponse
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import kr.flooding.backend.global.util.StudentUtil.Companion.calcYearToGrade
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class FetchUserUsecase(
	private val userUtil: UserUtil,
	private val s3Adapter: S3Adapter
) {
	fun execute(): FetchUserInfoResponse {
		val user = userUtil.getUser()
		val studentInfo = Optional.ofNullable(user.studentInfo)

		val studentInfoResponse =
			studentInfo.map {
				val year = requireNotNull(it.year)
				val classroom = requireNotNull(it.classroom)
				val number = requireNotNull(it.number)

				val grade = calcYearToGrade(year)
				val isGraduate = grade > 3
				StudentInfoResponse(
					grade = if (isGraduate) 0 else grade,
					isGraduate = isGraduate,
					classroom = classroom,
					number = number,
					year = year,
				)
			}

		val profileImageUrl = user.profileImageKey?.let {
			s3Adapter.generatePresignedUrl(it)
		}

		return FetchUserInfoResponse(
			id = requireNotNull(user.id),
			name = user.name,
			gender = user.gender,
			studentInfo = studentInfoResponse.getOrNull(),
			email = user.email,
			profileImageUrl = profileImageUrl,
		)
	}
}
