package kr.flooding.backend.domain.period.persistence.repository

import kr.flooding.backend.domain.period.persistence.entity.Period
import org.springframework.data.jpa.repository.JpaRepository

interface PeriodRepository : JpaRepository<Period, Int>
