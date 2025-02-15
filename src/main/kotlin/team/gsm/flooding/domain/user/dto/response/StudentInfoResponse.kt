package team.gsm.flooding.domain.user.dto.response

data class StudentInfoResponse(
	val isGraduate: Boolean,
	val grade: Int,
	val classroom: Int,
	val number: Int,
	val year: Int,
)
