package kr.flooding.backend.domain.music.usecase

import kr.flooding.backend.domain.music.dto.request.MusicOrderType
import kr.flooding.backend.domain.music.dto.response.FetchMusicResponse
import kr.flooding.backend.domain.music.repository.jdsl.MusicJdslRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class FetchMusicUsecase(
    private val musicJdslRepository: MusicJdslRepository,
) {
    fun execute(date: LocalDate, orderType: MusicOrderType): FetchMusicResponse =
        FetchMusicResponse.toDto(
            musicJdslRepository.findAllByCreatedDateOrderByMusicOrderType(date, orderType)
        )
}