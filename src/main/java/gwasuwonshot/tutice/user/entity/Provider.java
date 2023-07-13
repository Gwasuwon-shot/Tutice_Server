package gwasuwonshot.tutice.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
    LOCAL("PROVIDER_LOCAL","자체"),
    KAKAO("PROVIDER_KAKAO","카카오");
    private final String key;
    private final String value;
}
