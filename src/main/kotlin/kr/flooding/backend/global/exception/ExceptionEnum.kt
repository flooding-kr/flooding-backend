package kr.flooding.backend.global.exception

import org.springframework.http.HttpStatus

sealed interface ExceptionEnum {
	enum class AUTH(
		val status: HttpStatus,
		val reason: String,
	) {
		EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 비어있습니다."),
		NOT_APPROVED_USER(HttpStatus.UNAUTHORIZED, "아직 관리자 승인되지 않은 유저입니다."),
		EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
		UNSUPPORTED_TOKEN(HttpStatus.FORBIDDEN, "형식이 일치하지 않은 토큰입니다."),
		MALFORMED_TOKEN(HttpStatus.FORBIDDEN, "올바르지 않은 구성의 토큰입니다."),
		OTHER_TOKEN(HttpStatus.FORBIDDEN, "토큰을 검증하는 중 에러가 발생하였습니다."),
		NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "해당하는 리프레시 토큰을 찾을 수 없습니다."),
		INVALID_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "리프레시 토큰이 유효하지 않습니다."),
		DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "해당 이메일을 사용하는 회원이 이미 존재합니다."),
		DUPLICATED_STUDENT_INFO(HttpStatus.BAD_REQUEST, "해당 학번의 회원의 이미 존재합니다."),
		WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
		WRONG_YEAR(HttpStatus.BAD_REQUEST, "기수가 일치하지 않습니다."),
		NOT_FOUND_RESET_PASSWORD_REQUEST_CODE(HttpStatus.BAD_REQUEST, "해당하는 비밀번호 변경 인증코드를 찾을 수 없습니다."),
		NOT_VERIFIED_EMAIL(HttpStatus.UNAUTHORIZED, "이메일 인증을 진행하고 나서 로그인을 시도해주세요."),
		NOT_FOUND_VERIFY_CODE(HttpStatus.NOT_FOUND, "해당하는 이메일 인증 코드를 찾을 수 없습니다."),
		ALREADY_VERIFY_EMAIL(HttpStatus.BAD_REQUEST, "이미 해당 이메일은 인증되었습니다."),
		UNKNOWN_ERROR_EMAIL(HttpStatus.SERVICE_UNAVAILABLE, "이메일 전송 중 에러가 발생하였습니다."),
	}

	enum class USER(
		val status: HttpStatus,
		val reason: String,
	) {
		NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당하는 회원을 찾을 수 없습니다."),
		USER_MISMATCH(HttpStatus.BAD_REQUEST, "유저가 일치하지 않습니다."),
	}

	enum class NEIS(
		val status: HttpStatus,
		val reason: String,
	) {
		NOT_FOUND_LUNCH(HttpStatus.NOT_FOUND, "해당 날짜의 급식 일정을 찾을 수 없습니다."),
	}

	enum class UNKNOWN(
		val status: HttpStatus,
		val reason: String,
	) {
		UNKNOWN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "의도하지 않은 에러가 발생하였습니다."),
	}

	enum class CLUB(
		val status: HttpStatus,
		val reason: String,
	) {
		NOT_FOUND_CLUB(HttpStatus.NOT_FOUND, "해당하는 동아리를 찾을 수 없습니다."),
		NOT_FOUND_CLUB_MEMBER(HttpStatus.NOT_FOUND, "해당하는 동아리 구성원을 찾을 수 없습니다."),
		NOT_CLUB_LEADER(HttpStatus.FORBIDDEN, "해당 동아리의 리더가 아닙니다."),
		ALREADY_USED_CLUB_NAME(HttpStatus.BAD_REQUEST, "이미 사용중인 동아리명입니다."),
		EXISTS_PENDING_CLUB(HttpStatus.BAD_REQUEST, "동일한 유형의 동아리가 이미 심사를 대기중입니다."),
		ALREADY_JOINED_CLUB(HttpStatus.BAD_REQUEST, "이미 참여중인 동일한 유형의 동아리가 존재합니다."),
		ALREADY_APPLY_CLUB(HttpStatus.BAD_REQUEST, "이미 동일한 유형의 동아리에 지원 신청 했습니다."),
		NOT_APPROVED_CLUB(HttpStatus.BAD_REQUEST, "아직 관리자 승인이 되지 않은 동아리입니다."),
		NOT_CLUB_RECRUITING(HttpStatus.BAD_REQUEST, "구성원을 모집중인 동아리가 아닙니다."),
		ALREADY_SENT_INVITE(HttpStatus.BAD_REQUEST, "이미 해당 학생에게 보낸 초대가 존재합니다."),
		NOT_FOUND_CLUB_INVITE(HttpStatus.NOT_FOUND, "해당하는 동아리 초대를 찾을 수 없습니다."),
		INVALID_CLUB_INVITE_CODE(HttpStatus.BAD_REQUEST, "일치하지 않는 동아리 초대 코드입니다."),
		MISSING_ABSENCE_REASON(HttpStatus.BAD_REQUEST, "미출석 사유를 입력해야 합니다."),
		NOT_CLUB_MEMBER(HttpStatus.FORBIDDEN, "해당 동아리의 구성원이 아닙니다."),
		NOT_FOUND_CLUB_APPLICANT(HttpStatus.NOT_FOUND, "해당하는 동아리 지원을 찾을 수 없습니다."),
	}

	enum class CLASSROOM(
		val status: HttpStatus,
		val reason: String,
	) {
		NOT_FOUND_CLASSROOM(HttpStatus.NOT_FOUND, "해당하는 교실을 찾을 수 없습니다."),
		IS_HOMEBASE_CLASSROOM(HttpStatus.BAD_REQUEST, "해당 교실은 홈베이스입니다."),
		NOT_FOUND_TABLE(HttpStatus.NOT_FOUND, "해당하는 자리를 찾을 수 없습니다."),
		ALREADY_JOINED_ATTENDANCE(HttpStatus.BAD_REQUEST, "이미 다른 자리를 사용중인 팀원이 있습니다."),
		EXISTS_USED_TABLE(HttpStatus.BAD_REQUEST, "이미 사용중인 자리입니다."),
		NOT_FOUND_ATTENDANCE(HttpStatus.BAD_REQUEST, "출결 기록을 찾을 수 없습니다."),
		NOT_FOUND_GROUP(HttpStatus.NOT_FOUND, "홈베이스 그룹이 없습니다."),
		PROPOSER_CANNOT_BE_PARTICIPANT(HttpStatus.BAD_REQUEST, "신청자가 참가자에 포함될 수 없습니다."),
	}

	enum class HOMEBASE(
		val status: HttpStatus,
		val reason: String,
	) {
		MAX_CAPACITY_EXCEEDED(HttpStatus.BAD_REQUEST, "해당 테이블의 자리보다 신청자가 많습니다."),
	}

	enum class ATTENDANCE(
		val status: HttpStatus,
		val reason: String,
	) {
		NOT_FOUND_PERIOD_INFO(HttpStatus.NOT_FOUND, "교시 정보를 찾을 수 없습니다."),
		ATTENDANCE_OUT_OF_TIME_RANGE(HttpStatus.BAD_REQUEST, "현재 시간에 해당하는 교시가 아닙니다."),
		NOT_FOUND_ATTENDANCE_INFO(HttpStatus.NOT_FOUND, "출석 정보를 찾을 수 없습니다."),
	}

	enum class MUSIC(
		val status: HttpStatus,
		val reason: String,
	) {
		ALREADY_REQUESTED_MUSIC(HttpStatus.BAD_REQUEST, "이미 신청한 음악이 존재합니다."),
		INVALID_MUSIC_URL(HttpStatus.BAD_REQUEST, "잘못된 형식의 음악 URL 입니다."),
		NOT_FOUND_MUSIC(HttpStatus.NOT_FOUND, "존재하지 않는 음악 URL 입니다."),
		TOO_MANY_MUSIC_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "기상 음악에 대한 요청이 너무 많습니다."),
		ALREADY_MUSIC_LIKE(HttpStatus.BAD_REQUEST, "기상 음악 좋아요가 이미 처리된 상태입니다."),
	}

	enum class FILE(
		val status: HttpStatus,
		val reason: String,
	) {
		INVALID_IMAGE_EXTENSION(HttpStatus.BAD_REQUEST, "jpg, png, jpeg 형식의 이미지만 업로드할 수 있습니다."),
	}

	@Suppress("ktlint:standard:class-naming")
	enum class SELF_STUDY(
		val status: HttpStatus,
		val reason: String,
	) {
		NO_SELF_STUDY_TODAY(HttpStatus.BAD_REQUEST, "자습이 있는 날이 아닙니다."),
		MAX_CAPACITY_SELF_STUDY(HttpStatus.BAD_REQUEST, "가능한 자습 인원이 초과되었습니다."),
		ALREADY_RESERVE_SELF_STUDY(HttpStatus.BAD_REQUEST, "이미 자습 신청을 했습니다."),
		EXISTS_RESERVE_SELF_STUDY_HISTORY(HttpStatus.BAD_REQUEST, "이미 자습 신청을 한번 이상 시도했습니다."),
		NOT_FOUND_SELF_STUDY_ROOM(HttpStatus.NOT_FOUND, "자습 신청 정보를 받아올 수 없습니다."),
		ALREADY_CANCELLED_SELF_STUDY(HttpStatus.BAD_REQUEST, "이미 자습 신청이 취소된 상태입니디."),
		SELF_STUDY_OUT_OF_TIME_RANGE(HttpStatus.BAD_REQUEST, "정해진 시간에 자습 신청을 해주세요."),
		NOT_FOUND_SELF_STUDY_RESERVATION(HttpStatus.NOT_FOUND, "자습 예약을 찾을 수 없습니다."),
	}
}

fun ExceptionEnum.CLASSROOM.toPair() = Pair(this.status, this.reason)

fun ExceptionEnum.USER.toPair() = Pair(this.status, this.reason)

fun ExceptionEnum.UNKNOWN.toPair() = Pair(this.status, this.reason)

fun ExceptionEnum.AUTH.toPair() = Pair(this.status, this.reason)

fun ExceptionEnum.CLUB.toPair() = Pair(this.status, this.reason)

fun ExceptionEnum.NEIS.toPair() = Pair(this.status, this.reason)

fun ExceptionEnum.HOMEBASE.toPair() = Pair(this.status, this.reason)

fun ExceptionEnum.ATTENDANCE.toPair() = Pair(this.status, this.reason)

fun ExceptionEnum.MUSIC.toPair() = Pair(this.status, this.reason)

fun ExceptionEnum.FILE.toPair() = Pair(this.status, this.reason)

fun ExceptionEnum.SELF_STUDY.toPair() = Pair(this.status, this.reason)
