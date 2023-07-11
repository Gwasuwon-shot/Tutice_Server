package gwasuwonshot.tutice.external.firebase;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class FCMTestRequestDto {
    private String deviceToken;
}
