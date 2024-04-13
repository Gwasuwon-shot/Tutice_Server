package gwasuwonshot.tutice.user.dto.request;

import gwasuwonshot.tutice.common.resolver.enumValue.Enum;
import gwasuwonshot.tutice.user.entity.Provider;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    private String socialToken;

    @NotBlank
    @Enum(enumClass = Provider.class, ignoreCase = true, message = "잘못된 provider 값 입니다.")
    private String provider;

}
