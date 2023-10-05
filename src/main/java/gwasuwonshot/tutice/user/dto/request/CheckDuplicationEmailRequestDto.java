package gwasuwonshot.tutice.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CheckDuplicationEmailRequestDto {
    @NotBlank
    private String email;
}