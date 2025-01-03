package team.gsm.flooding.domain.user.entity

enum class Role (
	name: String
) {
	ROLE_USER("ROLE_USER"),
	ROLE_TEACHER("ROLE_TEACHER"),
	ROLE_DOMITORY_ADMIN("ROLE_DOMITORY_ADMIN"),
	ROLE_DEVELOPER("ROLE_DEVELOPER"),
	ROLE_CLUB_LEADER("ROLE_CLUB_LEADER")
}