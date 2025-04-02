package kr.flooding.backend.domain.period.repository

import kr.flooding.backend.domain.period.entity.Period
import org.springframework.data.jpa.repository.JpaRepository

interface PeriodRepository : JpaRepository<Period, Int>
