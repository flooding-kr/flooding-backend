package kr.flooding.backend.domain.homebaseTable.repository.jdsl

import kr.flooding.backend.domain.homebaseTable.entity.HomebaseTable
import java.util.Optional

interface HomebaseTableJdslRepository {
	fun findWithHomebaseByTableNumberAndFloor(
		tableNumber: Int,
		homebaseFloor: Int,
	): Optional<HomebaseTable>

	fun findWithHomebaseByFloor(homebaseFloor: Int): List<HomebaseTable>
}
