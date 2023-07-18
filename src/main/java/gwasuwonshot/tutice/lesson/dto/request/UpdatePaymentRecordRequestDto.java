package gwasuwonshot.tutice.lesson.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "페이먼트레코드아이디로 입금일 등록 Dto")
public class UpdatePaymentRecordRequestDto {
    @NotNull( message = "paymentRecordIdx가 없습니다.")
    @Schema(description = "paymentRecordIdx")
    private Long paymentRecordIdx;


    @NotBlank( message = "paymentDate가 없습니다.")
    @Schema(description = "paymentDate")
    private String paymentDate;
}
