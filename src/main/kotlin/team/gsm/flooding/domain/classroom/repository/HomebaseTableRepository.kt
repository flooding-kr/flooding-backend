package team.gsm.flooding.domain.classroom.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.gsm.flooding.domain.classroom.entity.HomebaseTable
import java.util.Optional

interface HomebaseTableRepository: JpaRepository<HomebaseTable, Long> {
	fun findByTableNumberAndHomebaseFloor(tableNumber: Int, homebaseFloor: Int): Optional<HomebaseTable>
	fun findByHomebaseFloor(homebaseFloor: Int): List<HomebaseTable>
}