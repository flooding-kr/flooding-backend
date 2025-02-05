package team.gsm.flooding.global.exception

import org.springframework.http.HttpStatus

enum class ExceptionEnum (
	val status: HttpStatus,
	val reason: String
) {
	// JWT
	EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 비어있습니다."),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
	UNSUPPORTED_TOKEN(HttpStatus.FORBIDDEN, "형식이 일치하지 않은 토큰입니다."),
	MALFORMED_TOKEN(HttpStatus.FORBIDDEN, "올바르지 않은 구성의 토큰입니다."),
	OTHER_TOKEN(HttpStatus.FORBIDDEN, "토큰을 검증하는 중 에러가 발생하였습니다."),
	NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "해당하는 리프레시 토큰을 찾을 수 없습니다."),
	INVALID_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "리프레시 토큰이 유효하지 않습니다."),

	// 인증
	DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "해당 이메일을 사용하는 회원이 이미 존재합니다."),
	DUPLICATED_STUDENT_INFO(HttpStatus.BAD_REQUEST, "해당 학번의 회원의 이미 존재합니다."),
	WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당하는 회원을 찾을 수 없습니다."),
	WRONG_YEAR(HttpStatus.BAD_REQUEST, "기수가 일치하지 않습니다."),

	// 이메일
	UNKNOWN_ERROR_EMAIL(HttpStatus.SERVICE_UNAVAILABLE, "이메일 전송 중 에러가 발생하였습니다."),
	NOT_VERIFIED_EMAIL(HttpStatus.UNAUTHORIZED, "이메일 인증을 진행하고 나서 로그인을 시도해주세요."),
	NOT_FOUND_VERIFY_CODE(HttpStatus.NOT_FOUND, "해당하는 이메일 인증 코드를 찾을 수 없습니다."),
	ALREADY_VERIFY_EMAIL(HttpStatus.BAD_REQUEST, "이미 해당 이메일은 인증되었습니다."),

	// 홈베이스
	NOT_FOUND_TABLE(HttpStatus.NOT_FOUND, "해당하는 자리를 찾을 수 없습니다."),
	ALREADY_JOINED_ATTENDANCE(HttpStatus.BAD_REQUEST, "이미 다른 자리를 사용중인 팀원이 있습니다."),
	EXISTS_USED_TABLE(HttpStatus.BAD_REQUEST, "이미 사용중인 자리입니다."),
	NOT_FOUND_ATTENDANCE(HttpStatus.BAD_REQUEST, "출결 기록을 찾을 수 없습니다."),
	NOT_FOUND_GROUP(HttpStatus.BAD_REQUEST, "홈베이스 그룹이 없습니다."),
	NO_PERMISSION(HttpStatus.BAD_REQUEST, "권한이 없습니다."),

	// 급식
	NOT_FOUND_LUNCH(HttpStatus.NOT_FOUND, "해당 날짜의 급식 일정을 찾을 수 없습니다."),
}