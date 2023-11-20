package gwasuwonshot.tutice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserNameResponse {
    private String userName;

    public static GetUserNameResponse of(String name) {
        return new GetUserNameResponse(name);
    }
}
