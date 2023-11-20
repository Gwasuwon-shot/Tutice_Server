package gwasuwonshot.tutice.user.dto.request;

import gwasuwonshot.tutice.common.resolver.enumValue.Enum;
import gwasuwonshot.tutice.user.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class LocalSignUpRequest {


    @Enum(enumClass = Role.class, ignoreCase = true, message ="잘못된 role 값 입니다.")
    private String role;

    @NotBlank
    @Size(min=2, message = "이름은 최소 2자 이상 입력해주세요.")
    private String name;

    @NotBlank
    @Email(message = "올바른 이메일 형식으로 입력해 주세요.")
    private String email;


    @NotNull
    @Pattern(
            regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "8~16자의 영문, 숫자, 특수문자를 모두 포함해주세요."
    )
    private String password;

    private Boolean isMarketing;
}
