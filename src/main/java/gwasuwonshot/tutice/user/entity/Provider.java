package gwasuwonshot.tutice.user.entity;

import gwasuwonshot.tutice.common.resolver.enumValue.EnumModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Provider implements EnumModel {
    LOCAL("PROVIDER_LOCAL", "자체"),
    KAKAO("PROVIDER_KAKAO","카카오"),
    NAVER("PROVIDER_NAVER","네이버"),
    TEMP("PROVIDER_TEMP","임시");

    private final String key;
    private final String value;

    public static Provider getProviderByValue(String value){
        return Arrays.stream(Provider.values())
                .filter(p -> p.getValue().equals(value))
                .findAny().orElseThrow();
    }
}
