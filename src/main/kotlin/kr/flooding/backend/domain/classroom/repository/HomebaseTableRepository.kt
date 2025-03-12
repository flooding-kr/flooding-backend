package kr.flooding.backend.domain.classroom.repository

import kr.flooding.backend.domain.classroom.entity.HomebaseTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface HomebaseTableRepository : JpaRepository<HomebaseTable, Long> {
	fun findByTableNumberAndHomebaseFloor(
		tableNumber: Int,
		homebaseFloor: Int,
	): Optional<HomebaseTable>

	@Query(
		"""
			SELECT t
			FROM HomebaseTable t
			JOIN FETCH t.homebase
			WHERE t.homebase.floor = :homebaseFloor
		""",
	)
	fun findWithHomebaseByFloor(homebaseFloor: Int): List<HomebaseTable>
}
