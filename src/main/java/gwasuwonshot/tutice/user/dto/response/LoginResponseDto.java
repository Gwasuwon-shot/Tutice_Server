package gwasuwonshot.tutice.user.dto.response;

import gwasuwonshot.tutice.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private UserResponseDto user;


    public static LoginResponseDto of(String accessToken, String refreshToken, User user) {
        return new LoginResponseDto(accessToken, refreshToken, UserResponseDto.of(user.getName(), user.getRole().getValue()));
    }
}
