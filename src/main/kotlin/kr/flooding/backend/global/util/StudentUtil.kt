package kr.flooding.backend.global.util

import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class StudentUtil {
	companion object {
		fun calcYearToGrade(year: Int): Int {
			val nowDate = LocalDate.now()
			return nowDate.year - 2015 - year
		}

		fun calcStudentNumber(
			year: Int,
			classroom: Int,
			number: Int,
		): String {
			val grade = calcYearToGrade(year)
			return "$grade$classroom${String.format("%02d", number)}"
		}

		fun calcGradeToYear(grade: Int): Int {
			val currentDate = LocalDate.now()
			return currentDate.year - 2015 - grade
		}
	}
}
