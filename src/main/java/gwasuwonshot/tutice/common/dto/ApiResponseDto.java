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
public class ApiResponseDto<T> {

    private final int code;
    private final String message;
    private T data;

    public static ApiResponseDto success(SuccessStatus successStatus) {
        return new ApiResponseDto<>(successStatus.getHttpStatusCode(), successStatus.getMessage());
    }

    public static <T> ApiResponseDto<T> success(SuccessStatus successStatus, T data) {
        return new ApiResponseDto<T>(successStatus.getHttpStatusCode(), successStatus.getMessage(), data);
    }

    public static ApiResponseDto error(ErrorStatus errorStatu) {
        return new ApiResponseDto<>(errorStatu.getHttpStatusCode(), errorStatu.getMessage());
    }

    public static ApiResponseDto error(ErrorStatus errorStatus, String message) {
        return new ApiResponseDto<>(errorStatus.getHttpStatusCode(), message);
    }
}