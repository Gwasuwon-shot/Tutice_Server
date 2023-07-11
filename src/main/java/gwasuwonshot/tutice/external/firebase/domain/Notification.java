package gwasuwonshot.tutice.external.firebase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Notification { //FCM에서 받는 모든 mobile os를 아우를수 있는 Notification 형식
    private String title;
    private String body;
    private String image;

    public static Notification toEntity(String title, String body) {
        return Notification.builder()
                .title(title)
                .body(body)
                .image(null)
                .build();
    }
}
