package gwasuwonshot.tutice.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.exception.SuccessStatus;



@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final int code;
    private final String message;
    private T data;

    public static ApiResponse success(SuccessStatus successStatus) {
        return new ApiResponse<>(successStatus.getHttpStatusCode(), successStatus.getMessage());
    }

    public static <T> ApiResponse<T> success(SuccessStatus successStatus, T data) {
        return new ApiResponse<T>(successStatus.getHttpStatusCode(), successStatus.getMessage(), data);
    }

    public static ApiResponse error(ErrorStatus errorStatu) {
        return new ApiResponse<>(errorStatu.getHttpStatusCode(), errorStatu.getMessage());
    }

    public static ApiResponse error(ErrorStatus errorStatus, String message) {
        return new ApiResponse<>(errorStatus.getHttpStatusCode(), message);
    }
}