package kr.flooding.backend.domain.classroom.repository

import kr.flooding.backend.domain.classroom.entity.HomebaseTable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface HomebaseTableRepository : JpaRepository<HomebaseTable, Long> {
	fun findByTableNumberAndHomebaseFloor(
		tableNumber: Int,
		homebaseFloor: Int,
	): Optional<HomebaseTable>

	fun findByHomebaseFloor(homebaseFloor: Int): List<HomebaseTable>
}
