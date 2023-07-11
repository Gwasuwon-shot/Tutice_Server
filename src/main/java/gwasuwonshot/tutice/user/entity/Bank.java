package gwasuwonshot.tutice.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bank {
    SINHAN("BANK_SINHAN","신한은행"),
    KB("BANK_KB","KB국민은행"),
    WOORI("BANK_WOORI","우리은행"),
    HANA("BANK_HANA","하나은행"),
    SC("BANK_SC","SC제일은행"),
    K("BANK_K","케이뱅크"),
    KAKAO("BANK_KAKAO","카카오뱅크"),
    TOSS("BANK_TOSS","토스뱅크"),
    NH("BANK_NH","NH농협은행");

    private final String key;
    private final String value;
}
