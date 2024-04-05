package gwasuwonshot.tutice.user.entity;

import gwasuwonshot.tutice.common.resolver.enumValue.EnumModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public enum Bank implements EnumModel {

    NH("BANK_NH","NH농협은행"),
    KB("BANK_KB","KB국민은행"),
    IBK("BANK_IBK","IBK기업은행"),
    SINHAN("BANK_SINHAN","신한은행"),
    WOORI("BANK_WOORI","우리은행"),
    HANA("BANK_HANA","하나은행"),
    KAKAO("BANK_KAKAO","카카오뱅크"),
    K("BANK_K","케이뱅크"),
    TOSS("BANK_TOSS","토스뱅크"),
    SC("BANK_SC","SC제일은행"),
    BNK_G("BANK_BNK_G","경남은행"),
    KJ("BANK_KJ","광주은행"),
    DGB("BANK_DGB","대구은행"),
    BNK_B("BANK_BNK_B","부산은행"),
    SH("BANK_SH","수협은행"),
    JB("BANK_JB","전북은행"),
    JJ("BANK_JJ","제주은행"),
    CITI("BANK_CITI","한국시티은행"),
    MG("BANK_MG","새마을금고"),
    NACUFOK("BANK_NACUFOK","신협"),
    SB("BANK_SB","상호저축은행"),
    SJ("BANK_SJ","산림조합"),
    POST("BANK_POST","우체국"),
    DB("BANK_DB","도이치"),
    BOA("BANK_BOA","뱅크오브아메리카"),
    ICBC("BANK_ICBC","중국공상은행"),
    CCB("BANK_CCB","중국건설은행"),
    BOC("BANK_BOC","중국은행"),
    BNP("BANK_BNP","BNP파리바"),
    HSBC("BANK_HSBC","HSBC"),
    JP("BANK_JP","JP모건"),

    ;

    private final String key;
    private final String value;

    private static HashMap<String, Bank> bankByValue = new HashMap<>();
    static {
        Arrays.stream(values()).forEach(b -> {
            bankByValue.put(b.getValue(),b);
        });
    }

    public static Bank getByValue(String value){
        return bankByValue.getOrDefault(value,null);
    }


}
