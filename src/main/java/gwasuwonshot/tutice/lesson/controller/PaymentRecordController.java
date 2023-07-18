package gwasuwonshot.tutice.lesson.controller;


import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordView.GetPaymentRecordViewResponseDto;
import gwasuwonshot.tutice.lesson.service.PaymentRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-record")
@Tag(name = "PaymentRecord", description = "입금기록 API Document")
public class PaymentRecordController {
    private final PaymentRecordService paymentRecordService;

    @GetMapping("/{lessonIdx}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetPaymentRecordViewResponseDto> getPaymentRecordView(
            @UserIdx final Long userIdx,
            @PathVariable final Long lessonIdx) {

        return ApiResponseDto.success(SuccessStatus.GET_PAYMENT_RECORD_POST_VIEW_SUCCESS,
                GetPaymentRecordViewResponseDto.of(paymentRecordService.getPaymentRecordView(userIdx, lessonIdx),
                        DateAndTimeConvert.nowLocalDateConvertString()));


    }

}
