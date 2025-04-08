package kr.flooding.backend.domain.user.model

data class StudentInfoModel(
	val isGraduate: Boolean,
	val grade: Int,
	val classroom: Int,
	val number: Int,
	val year: Int,
)
