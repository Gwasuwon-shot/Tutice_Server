package gwasuwonshot.tutice.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access =  AccessLevel.PRIVATE)
public enum SuccessStatus {
    /**
     * 200 OK
     */
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    NEW_TOKEN_SUCCESS(HttpStatus.OK, "토큰 재발급에 성공했습니다."),
    GET_TODAY_DATE_SUCCESS(HttpStatus.OK,"오늘 날짜와 요일을 가져오는데 성공했습니다."),


    GET_LESSON_BY_USER_SUCCESS(HttpStatus.OK, "유저별로 연결된 수업 여부를 가져오는데 성공했습니다."),
    GET_LESSON_SCHEDULE_SUCCESS(HttpStatus.OK,"수업 내역(출결 상황) 가져오기 성공"),
    GET_SCHEDULE_BY_USER_SUCCESS(HttpStatus.OK, "스케줄 메인 뷰를 가져오는데 성공했습니다."),
    GET_MISSING_ATTENDANCE_SCHEDULE_SUCCESS(HttpStatus.OK, "선생님의 출결 누락 뷰를 가져오는데 성공했습니다."),
    GET_MISSING_MAINTENANCE_LESSON_SUCCESS(HttpStatus.OK, "연장여부를 알려주지 않은 수업 리스트 가져오기 성공"),
    GET_PAYMENT_RECORD_POST_VIEW_SUCCESS(HttpStatus.OK,"입금 등록 뷰 정보 가져오기 성공"),
    GET_PAYMENT_RECORD_SUCCESS(HttpStatus.OK,"입금 내역 가져오기 성공"),



    GET_TODAY_SCHEDULE_BY_TEACHER_SUCCESS(HttpStatus.OK, "선생님 메인 뷰의 오늘의 수업 배너를 가져오는데 성공했습니다."),
    GET_LATEST_SCHEDULE_BY_TEACHER(HttpStatus.OK,"선생님 메인 뷰 오늘의 수업/다가오는 수업을 가져오는데 성공했습니다."),
    GET_LESSON_BY_TEACHER_SUCCESS(HttpStatus.OK,"선생님 수업 관리 뷰 조회 성공"),
    GET_TEMPORARY_SCHEDULE_SUCCESS(HttpStatus.OK,"임시 스케줄 가져오기 성공"),



    GET_LESSON_DETAIL_BY_PARENTS_SUCCESS(HttpStatus.OK, "선생님이 연결된 수업의 상세정보를 가져오는데 성공했습니다."),
    GET_TODAY_SCHEDULE_BY_PARENTS_SUCCESS(HttpStatus.OK, "학부모 메인 뷰의 오늘의 수업 배너를 가져오는데 성공했습니다."),
    GET_LESSON_BY_PARENTS_SUCCESS(HttpStatus.OK,"학부모 메인 뷰의 수업관리 조회 성공"),



    UPDATE_PAYMENT_RECORD_SUCCESS(HttpStatus.OK,"입금 등록 성공"),
    UPDATE_DEVICE_TOKEN_SUCCESS(HttpStatus.OK, "디바이스 토큰 업데이트 성공"),
    UPDATE_LESSON_PARENTS_SUCCESS(HttpStatus.OK, "수업과 학부모 연결 성공"),
    UPDATE_SCHEDULE(HttpStatus.OK,"스케줄 수정에 성공했습니다."),
    UPDATE_SCHEDULE_ATTENDANCE_SUCCESS(HttpStatus.OK,"출석 상태 변경 성공"),



    REQUEST_ATTENDANCE_NOTIFICATION_SUCCESS(HttpStatus.OK,"학부모에게 출결알람 보내기 성공"),
    REQUEST_PAYMENT_RECORD_NOTIFICATION_SUCCESS(HttpStatus.OK,"수업비 알림 전송 성공"),


    /**
     * 201 CREATED
     */
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료됐습니다."),
    CREATE_LESSON_SUCCESS(HttpStatus.CREATED, "수업 생성이 완료됐습니다."),
    AUTO_CREATE_SCHEDULE_FROM_LESSON_MAINTENANCE_SUCCESS(HttpStatus.CREATED,"수업 연장으로 다음 사이클 스케쥴 자동생성 성공"),
    FINISH_LESSON_SUCCESS(HttpStatus.CREATED,"수업 종료 성공"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
