package gwasuwonshot.tutice.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorStatus {

    /**
     * 400 BAD REQUEST
     */
    REQUEST_VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
    NO_REQUEST_PARAMETER_EXCEPTION(HttpStatus.BAD_REQUEST, "요청 파라미터 값이 없습니다"),
    VALIDATION_WRONG_TYPE_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 타입이 입력되었습니다"),
    PARAMETER_TYPE_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "파라미터의 타입이 잘못됐습니다"),
    INVALID_PASSWORD_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 비밀번호가 입력됐습니다."),
    INVALID_DATE_EXCEPTION(HttpStatus.BAD_REQUEST, "날짜의 형태는 yyyy-mm-dd 형태여야합니다."),
    INVALID_TIME_EXCEPTION(HttpStatus.BAD_REQUEST, "시간의 형태는 HH:mm:00의 형태여야합니다."),
    NULL_ACCESS_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "토큰 값이 없습니다."),
    INVALID_LESSON_EXCEPTION(HttpStatus.BAD_REQUEST,"유효하지 않은 lesson 입니다."),
    ALREADY_UPDATE_SCHEDULE_ATTENDANCE_EXCEPTION(HttpStatus.BAD_REQUEST,"출결상태가 존재하는 스케줄입니다."),
    INVALID_SCHEDULE_DATE_EXCEPTION(HttpStatus.BAD_REQUEST,"출결상태가 존재하는 스케쥴 이전으로는 날짜를 변경할 수 없습니다."),
    INVALID_PAYMENT_RECORD_EXCEPTION(HttpStatus.BAD_REQUEST,"이미 입금된 기록은 수정할 수 없습니다."),
    UNCONNECTED_LESSON_PAYMENT_RECORD_EXCEPTION(HttpStatus.BAD_REQUEST,"레슨과 입금기록이 연결되지 않았습니다."),
    INVALID_ATTENDANCE_DATE_EXCEPTION(HttpStatus.BAD_REQUEST,"미래 출결 상태를 미리 변경할 수 없습니다."),
    INVALID_ATTENDANCE_STATUS_EXCEPTION(HttpStatus.BAD_REQUEST,"취소 상태의 스케줄은 수정할 수 없습니다."),
    INVALID_ATTENDANCE_SCHEDULE_EXCEPTION(HttpStatus.BAD_REQUEST,"출결이 누락된 수업 출석체크를 먼저 진행해야 합니다."),
    INVALID_SCHEDULE_EXCEPTION(HttpStatus.BAD_REQUEST,"유효하지 않은 schedule 입니다."),
    INVALID_LESSON_CODE_EXCEPTION(HttpStatus.BAD_REQUEST,"유효하지 않은 레슨코드입니다."),
    NOTIFICATION_FAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "알림 발송에 실패했습니다."),




    /**
     * 401 UNAUTHORIZED
     */
    TOKEN_TIME_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_REFRESH_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    INVALID_ACCESS_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 엑세스 토큰입니다."),
    INVALID_ROLE_EXCEPTION(HttpStatus.UNAUTHORIZED,"유효하지 않은 역할의 유저입니다."),

    /**
     * 404 NOT FOUND
     */
    NOT_FOUND_USER_EXCEPTION(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다"),
    NOT_FOUND_PAYMENT_RECORD_EXCEPTION(HttpStatus.NOT_FOUND, "존재하지 않는 입금기록입니다"),
    NOT_FOUND_LESSON_EXCEPTION(HttpStatus.NOT_FOUND, "수업을 찾을 수 없습니다."),
    NOT_FOUND_SCHEDULE_EXCEPTION(HttpStatus.NOT_FOUND,"스케줄을 찾을 수 없습니다."),
    NOT_FOUND_LESSON_PARENTS_EXCEPTION(HttpStatus.NOT_FOUND, "레슨에 학부모가 연결되지 않았습니다."),
    NOT_ALLOWED_NOTIFICATION_EXCEPTION(HttpStatus.NOT_FOUND, "푸쉬알림 상대가 알림을 허용하지 않았습니다."),




    /**
     * 409 CONFLICT
     */
    ALREADY_EXIST_USER_EXCEPTION(HttpStatus.CONFLICT, "이미 존재하는 유저입니다"),
    ALREADY_EXIST_LESSON_PARENTS_EXCEPTION(HttpStatus.CONFLICT, "이미 학부모가 등록된 수업입니다."),
    ALREADY_FINISHED_LESSON_EXCEPTION(HttpStatus.CONFLICT, "이미 종료된 수업입니다."),


    /**
     * 500 INTERNAL SERVER ERROR
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생했습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
