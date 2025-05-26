package kr.flooding.backend.domain.selfStudy.persistence.repository.jdsl

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyRoom
import java.util.*

interface SelfStudyRoomJdslRepository {
    fun findByIdIsNotNullWithPessimisticLock(): Optional<SelfStudyRoom>
}
