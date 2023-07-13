package gwasuwonshot.tutice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String name;
    private String role;

    public static UserResponseDto of(String name, String role) {
        return new UserResponseDto(name, role);
    }
}
