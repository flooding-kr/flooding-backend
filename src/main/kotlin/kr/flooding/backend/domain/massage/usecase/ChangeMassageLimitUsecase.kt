package kr.flooding.backend.domain.massage.usecase

import kr.flooding.backend.domain.massage.dto.ChangeMassageLimitRequest
import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChangeMassageLimitUsecase(
    private val massageJpaRepository: MassageJpaRepository,
) {
    fun execute(request: ChangeMassageLimitRequest) {
        val massage = massageJpaRepository.findByIdIsNotNull().orElseThrow {
            HttpException(ExceptionEnum.MASSAGE.NOT_FOUND_MASSAGE_ROOM.toPair())
        }

        massage.updateLimit(request.limit)
    }
}