package gwasuwonshot.tutice.external.firebase.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FCMMessage {
    private boolean validateOnly;
    private Message message;

    public static FCMMessage makeMessage(String targetToken, String title, String body) {
        return FCMMessage.builder()
                .message(Message.toEntity(targetToken, title, body))
                .validateOnly(false)
                .build();
    }
}
