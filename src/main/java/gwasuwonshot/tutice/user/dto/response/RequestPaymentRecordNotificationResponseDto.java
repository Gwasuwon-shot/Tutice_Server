package gwasuwonshot.tutice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPaymentRecordNotificationResponseDto {
    private Long lessonIdx;
    public static RequestPaymentRecordNotificationResponseDto of(Long lessonIdx) {
        return new RequestPaymentRecordNotificationResponseDto(lessonIdx);
    }

}