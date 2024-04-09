package gwasuwonshot.tutice.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import gwasuwonshot.tutice.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserResponse user;


    public static LoginResponse of(String accessToken, String refreshToken, User user) {
        return new LoginResponse(accessToken, refreshToken, UserResponse.of(user.getName(), user.getRole().getValue()));
    }

    public static LoginResponse of(String accessToken, String refreshToken) {
        return new LoginResponse(accessToken, refreshToken, null);
    }
}
