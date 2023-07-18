package gwasuwonshot.tutice.user.entity;

import gwasuwonshot.tutice.common.resolver.enumValue.EnumModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role implements EnumModel {
    ADMIN("ROLE_ADMIN","관리자"),
    PARENTS("ROLE_PARENTS","부모님"),
    TEACHER("ROLE_TEACHER","선생님");

    private final String key;
    private final String value;



    public static Role getRoleByValue(String value){
        return Arrays.stream(Role.values())
                .filter(r -> r.getValue().equals(value))
                .findAny().orElseThrow();
    }
}
