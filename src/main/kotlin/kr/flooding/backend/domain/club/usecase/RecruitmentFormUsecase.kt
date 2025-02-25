package kr.flooding.backend.domain.club.usecase

import kr.flooding.backend.domain.club.dto.request.CreateRecruitmentFormRequest
import kr.flooding.backend.domain.club.entity.ClubStatus
import kr.flooding.backend.domain.club.entity.RecruitmentForm
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.club.repository.RecruitmentFormRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.club.entity.RecruitmentFormField

@Service
@Transactional
class RecruitmentFormUsecase(
	private val recruitmentFormRepository: RecruitmentFormRepository,
	private val clubRepository: ClubRepository,
	private val userUtil: UserUtil,
) {
	fun execute(request: CreateRecruitmentFormRequest) {
		val currentUser = userUtil.getUser()

		val club =
			clubRepository
				.findById(request.clubId)
				.orElseThrow {
					HttpException(ExceptionEnum.NOT_FOUND_CLUB)
				}

		if (club.leader != currentUser) {
			throw HttpException(ExceptionEnum.NOT_CLUB_LEADER)
		}

		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.NOT_APPROVED_CLUB)
		}

		if (recruitmentFormRepository.existsByClub(club)) {
			throw HttpException(ExceptionEnum.ALREADY_EXIST_RECRUITMENT_FORM)
		}

		val fields =
			request.questions.map { question ->
				RecruitmentFormField(question = question)
			}

		val recruitmentForm =
			RecruitmentForm(
				club = club,
				recruitmentFormField = fields,
			)

		recruitmentFormRepository.save(recruitmentForm)
	}
}
