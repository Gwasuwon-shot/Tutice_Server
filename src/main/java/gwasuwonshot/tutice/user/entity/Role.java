package gwasuwonshot.tutice.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN","관리자"),
    PARENTS("ROLE_PARENTS","부모님"),
    TEACHER("ROLE_TEACHER","선생님");
    private final String key;
    private final String value;
}
