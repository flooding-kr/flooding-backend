package com.example.demo.domain.user.usecase

import com.example.demo.domain.user.entity.User
import com.example.demo.domain.user.repository.UserRepository
import com.example.demo.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GetUserUsecase (
	private val userRepository: UserRepository,
	private val userUtil: UserUtil
) {
	fun execute(): User { // TODO 임시 확인용
		return userUtil.getUser()
	}
}