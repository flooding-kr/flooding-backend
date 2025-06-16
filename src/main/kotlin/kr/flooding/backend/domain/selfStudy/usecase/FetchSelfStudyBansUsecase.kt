package kr.flooding.backend.domain.selfStudy.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.selfStudy.dto.common.response.FetchSelfStudyBanResponse
import kr.flooding.backend.domain.selfStudy.dto.web.response.FetchSelfStudyBansResponse
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyBanJpaRepository
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import org.springframework.stereotype.Service

@Service
@Transactional
class FetchSelfStudyBansUsecase(
    private val selfStudyBanJpaRepository: SelfStudyBanJpaRepository,
    private val s3Adapter: S3Adapter,
) {
    fun execute(): FetchSelfStudyBansResponse {
        val selfStudyBans = selfStudyBanJpaRepository.findAll()

        return FetchSelfStudyBansResponse(
            selfStudyBans.map { selfStudyBan ->
                FetchSelfStudyBanResponse.toDto(
                    selfStudyBan = selfStudyBan,
                    profileImage = selfStudyBan.student.profileImageKey?.let {
                        s3Adapter.generatePresignedUrl(it)
                    }
                )
            }
        )
    }
}