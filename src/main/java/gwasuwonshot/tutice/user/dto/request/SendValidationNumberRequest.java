package gwasuwonshot.tutice.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class SendValidationNumberRequest {
    @NotBlank
    @Pattern(regexp = "^01(0|1|[6-9])[0-9]{3,4}[0-9]{4}$",
            message = "올바른 전화번호를 입력해주세요.")
    private String phone;
}
