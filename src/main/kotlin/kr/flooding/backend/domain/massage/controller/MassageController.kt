package kr.flooding.backend.domain.massage.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.massage.dto.response.FetchMassageRankListResponse
import kr.flooding.backend.domain.massage.dto.response.FetchMassageResponse
import kr.flooding.backend.domain.massage.usecase.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Massage", description = "안마의자")
@RestController
@RequestMapping("/massage")
class MassageController(
	private val reserveMassageUsecase: ReserveMassageUsecase,
	private val cancelMassageUsecase: CancelMassageUsecase,
	private val fetchMassageStatusUsecase: FetchMassageStatusUsecase,
	private val fetchMassageRankUsecase: FetchMassageRankUsecase
) {
    @Operation(summary = "안마의자 신청")
    @PostMapping
    fun reserveMassage(): ResponseEntity<Unit> =
        reserveMassageUsecase.execute().run {
            ResponseEntity.ok().build()
        }

    @Operation(summary = "안마의자 취소")
    @DeleteMapping
    fun cancelMassage(): ResponseEntity<Unit> =
        cancelMassageUsecase.execute().run {
            ResponseEntity.ok().build()
        }

	@Operation(summary = "안마의자 인원 조회")
	@GetMapping("/status")
	fun fetchMassageCount(): ResponseEntity<FetchMassageResponse> {
		return fetchMassageStatusUsecase.execute().let {
			ResponseEntity.ok(it)
		}
	}

	@Operation(summary = "안마의자 순위 조회")
	@GetMapping("/rank")
	fun fetchMassageRank(): ResponseEntity<FetchMassageRankListResponse> {
		return fetchMassageRankUsecase.execute().let {
			ResponseEntity.ok(it)
		}
	}
}
