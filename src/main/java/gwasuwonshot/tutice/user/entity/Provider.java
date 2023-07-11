package gwasuwonshot.tutice.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
    LOKAL("PROVIDER_LOKAL","자체"),
    KAKAO("PROVIDER_KAKAO","카카오");
    private final String key;
    private final String value;
}