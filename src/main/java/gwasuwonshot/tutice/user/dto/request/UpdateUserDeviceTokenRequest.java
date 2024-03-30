package gwasuwonshot.tutice.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UpdateUserDeviceTokenRequest {

    private String deviceToken;

}
