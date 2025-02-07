package team.gsm.flooding.domain.attendance.dto.response

import team.gsm.flooding.domain.user.entity.Role
import team.gsm.flooding.domain.user.entity.StudentInfo
import team.gsm.flooding.domain.user.entity.User

class UserResponse(
    val email: String,
    val studentInfo: StudentInfo,
    val name: String,
    val role: List<Role>,

    ) {
        companion object {
            fun toDto(user: User): UserResponse {
                return UserResponse(
                    email = user.email,
                    studentInfo = user.studentInfo,
                    name = user.name,
                    role = user.roles,
                )
            }
        }
    }