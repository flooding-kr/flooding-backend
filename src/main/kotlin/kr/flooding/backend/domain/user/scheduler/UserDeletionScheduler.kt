package kr.flooding.backend.domain.user.scheduler

import kr.flooding.backend.domain.user.persistence.repository.jpa.UserDeletionJpaRepository
import kr.flooding.backend.domain.user.persistence.repository.jpa.UserJpaRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class UserDeletionScheduler (
	private val userDeletionJpaRepository: UserDeletionJpaRepository,
	private val userJpaRepository: UserJpaRepository,
) {
	@Scheduled(cron = "0 0 0 * * *")
	fun deleteRequestedUsers(){
		val currentDate = LocalDate.now()
		val deletionRequests = userDeletionJpaRepository.findByDeletedDateBefore(currentDate)

		val users = deletionRequests.map { it.user }
		userJpaRepository.deleteAll(users)
	}
}