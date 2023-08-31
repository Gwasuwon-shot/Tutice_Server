package gwasuwonshot.tutice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserNameResponseDto {
    private String userName;

    public static GetUserNameResponseDto of(String name) {
        return new GetUserNameResponseDto(name);
    }
}
