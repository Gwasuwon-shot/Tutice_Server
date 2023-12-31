package gwasuwonshot.tutice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String name;
    private String role;

    public static UserResponse of(String name, String role) {
        return new UserResponse(name, role);
    }
}
