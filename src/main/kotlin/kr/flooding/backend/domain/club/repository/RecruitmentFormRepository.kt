package kr.flooding.backend.domain.club.repository

import kr.flooding.backend.domain.club.entity.Club
import kr.flooding.backend.domain.club.entity.RecruitmentForm
import org.springframework.data.jpa.repository.JpaRepository

interface RecruitmentFormRepository : JpaRepository<RecruitmentForm, Int> {
	fun existsByClub(club: Club): Boolean
}
