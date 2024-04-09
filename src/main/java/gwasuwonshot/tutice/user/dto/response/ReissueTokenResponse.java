package gwasuwonshot.tutice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReissueTokenResponse {

    private String accessToken;
    private String refreshToken;

    public static ReissueTokenResponse of(String accessToken, String refreshToken) {
        return new ReissueTokenResponse(accessToken, refreshToken);
    }
}
