package gwasuwonshot.tutice.external.firebase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Message {
    private String token; //deviceToken
    private Notification notification;

    public static Message toEntity(String targetToken, String title, String body) {
        return Message.builder()
                .token(targetToken)
                .notification(Notification.toEntity(title, body))
                .build();
    }
}
