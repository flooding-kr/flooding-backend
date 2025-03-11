package kr.flooding.backend.domain.homebase.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import kr.flooding.backend.domain.classroom.entity.HomebaseTable
import kr.flooding.backend.domain.homebaseParticipants.entity.HomebaseParticipant
import kr.flooding.backend.domain.user.entity.User
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.util.UUID

@Entity
data class HomebaseGroup(
	@Id
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	val id: UUID? = null,

	@ManyToOne(fetch = FetchType.LAZY)
	val homebaseTable: HomebaseTable,

	val period: Int,

	@ManyToOne(fetch = FetchType.LAZY)
	val proposer: User,

	@BatchSize(size = 100)
	@OneToMany(mappedBy = "homebaseGroup", cascade = [CascadeType.ALL], orphanRemoval = true)
	val participants: MutableList<HomebaseParticipant> = mutableListOf(),

	@CreationTimestamp
	@Column(nullable = false)
	val attendedAt: LocalDate? = null,
) {
	fun addParticipant(participant: HomebaseParticipant) {
		participants.add(participant)
		participant.homebaseGroup = this
	}
}
