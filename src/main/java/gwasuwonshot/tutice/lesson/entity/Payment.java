package gwasuwonshot.tutice.lesson.entity;

import gwasuwonshot.tutice.common.resolver.enumValue.EnumModel;
import gwasuwonshot.tutice.user.entity.Bank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public enum Payment implements EnumModel {

    PRE_PAYMENT("PAYMENT_PRE","선불"),
    POST_PAYMENT("PAYMENT_POST","후불");
    private final String key;
    private final String value;

    private static HashMap<String, Payment> PaymentByValue = new HashMap<>();
    static {
        Arrays.stream(values()).forEach(p -> {
            PaymentByValue.put(p.getValue(),p);
        });
    }
    public static Payment getByValue(String value){
        return PaymentByValue.getOrDefault(value,null);
    }
    
}
