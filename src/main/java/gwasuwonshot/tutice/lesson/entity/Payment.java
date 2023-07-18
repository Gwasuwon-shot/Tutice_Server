package gwasuwonshot.tutice.lesson.entity;

import gwasuwonshot.tutice.common.resolver.enumValue.EnumModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Payment implements EnumModel {

    PRE_PAYMENT("PAYMENT_PRE","선불"),
    POST_PAYMENT("PAYMENT_POST","후불");
    private final String key;
    private final String value;

    public static Payment getPaymentByValue(String value){
        return Arrays.stream(Payment.values())
                .filter(p -> p.getValue().equals(value))
                .findAny().orElseThrow();
    }
}
