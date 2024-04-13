package gwasuwonshot.tutice.user.dto.request;

import gwasuwonshot.tutice.common.resolver.enumValue.Enum;
import gwasuwonshot.tutice.user.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class SignUpRequest {


    @Enum(enumClass = Role.class, ignoreCase = true, message ="잘못된 role 값 입니다.")
    private String role;

    @NotBlank
    @Size(min=2, message = "이름은 최소 2자 이상 입력해주세요.")
    private String name;

    @NotBlank
    @Pattern(regexp = "^01(0|1|[6-9])[0-9]{3,4}[0-9]{4}$",
            message = "올바른 전화번호를 입력해주세요.")
    private String phone;

    private Boolean isMarketing;
}
