package com.example.demo.domain.user.controller

import com.example.demo.domain.user.usecase.GetUserUsecase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController (
	private val getUserUsecase: GetUserUsecase
) {
	// TODO 임시 확인용
	@GetMapping
	fun getUserInfo() = getUserUsecase.execute()
}