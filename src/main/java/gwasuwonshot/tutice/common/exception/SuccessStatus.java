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
    GET_LESSON_BY_USER_SUCCESS(HttpStatus.OK, "유저별로 연결된 수업 여부를 가져오는데 성공했습니다."),
    GET_LESSON_DETAIL_BY_PARENTS_SUCCESS(HttpStatus.OK, "선생님이 연결된 수업의 상세정보를 가져오는데 성공했습니다."),

    /**
     * 201 CREATED
     */
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료됐습니다."),
    CREATE_LESSON_SUCCESS(HttpStatus.CREATED, "수업 생성이 완료됐습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
