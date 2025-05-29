package kr.flooding.backend.domain.club.usecase.admin

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.dto.web.request.ApproveClubRequest
import kr.flooding.backend.domain.club.enums.ClubStatus
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service

@Service
@Transactional
class ApproveClubUsecase(
    private val clubRepository: ClubRepository,
) {
    fun execute(request: ApproveClubRequest) {
        val club = clubRepository.findById(request.clubId).orElseThrow {
            HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
        }

        if (club.status != ClubStatus.PENDING) {
            throw HttpException(ExceptionEnum.CLUB.NOT_PENDING_CLUB.toPair())
        }

        club.approve()
    }
}