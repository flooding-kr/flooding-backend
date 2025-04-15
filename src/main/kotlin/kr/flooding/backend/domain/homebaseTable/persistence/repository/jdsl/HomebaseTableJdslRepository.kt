package kr.flooding.backend.domain.homebaseTable.persistence.repository.jdsl

import kr.flooding.backend.domain.homebaseTable.persistence.entity.HomebaseTable
import java.util.Optional

interface HomebaseTableJdslRepository {
	fun findWithHomebaseByTableNumberAndFloor(
		tableNumber: Int,
		homebaseFloor: Int,
	): Optional<HomebaseTable>

	fun findWithHomebaseByFloor(homebaseFloor: Int): List<HomebaseTable>
}
