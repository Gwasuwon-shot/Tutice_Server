package gwasuwonshot.tutice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPaymentRecordNotificationResponse {
    private Long lessonIdx;
    public static RequestPaymentRecordNotificationResponse of(Long lessonIdx) {
        return new RequestPaymentRecordNotificationResponse(lessonIdx);
    }

}