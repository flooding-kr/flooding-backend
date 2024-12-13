package com.example.demo.domain.user.repository

import com.example.demo.domain.user.entity.StudentInfo
import com.example.demo.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {
	fun findByEmail(email: String): Optional<User>

	fun existsByEmail(email: String): Boolean

	fun existsByStudentInfo(studentInfo: StudentInfo): Boolean
}