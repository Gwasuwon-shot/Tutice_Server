package gwasuwonshot.tutice.lesson.controller;


import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.lesson.dto.request.UpdatePaymentRecordRequestDto;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecord.GetPaymentRecordResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordView.GetPaymentRecordViewResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetPaymentRecordCycleResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecord.GetPaymentRecordByUserResponseDto;
import gwasuwonshot.tutice.lesson.service.PaymentRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-record")
@Tag(name = "PaymentRecord", description = "입금기록 API Document")
public class PaymentRecordController {
    private final PaymentRecordService paymentRecordService;

    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updatePaymentRecord(
            @UserIdx final Long userIdx,
            @Valid @RequestBody final UpdatePaymentRecordRequestDto request) {

        paymentRecordService.updatePaymentRecord(userIdx, request.getPaymentRecordIdx(),DateAndTimeConvert.stringConvertLocalDate(request.getPaymentDate()));

        return ApiResponseDto.success(SuccessStatus.UPDATE_PAYMENT_RECORD_SUCCESS);

    }


    @GetMapping("/{paymentRecordIdx}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetPaymentRecordViewResponseDto> getPaymentRecordView(
            @UserIdx final Long userIdx,
            @PathVariable final Long paymentRecordIdx) {


        return ApiResponseDto.success(SuccessStatus.GET_PAYMENT_RECORD_POST_VIEW_SUCCESS,
                GetPaymentRecordViewResponseDto.of(paymentRecordService.getPaymentRecordView(userIdx, paymentRecordIdx),
                        DateAndTimeConvert.nowLocalDateConvertString()));

    }

    @GetMapping("/lesson/{lessonIdx}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<GetPaymentRecordResponseDto>> getPaymentRecordByLesson(
            @UserIdx final Long userIdx,
            @PathVariable final Long lessonIdx
    ) {
        return ApiResponseDto.success(SuccessStatus.GET_PAYMENT_RECORD_SUCCESS,
                paymentRecordService.getPaymentRecordByLesson(userIdx, lessonIdx));
    }

    @GetMapping("/cycle/{paymentRecordIdx}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetPaymentRecordCycleResponseDto> getPaymentRecordCycle(
            @UserIdx final Long userIdx,
            @PathVariable final Long paymentRecordIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_PAYMENT_RECORD_CYCLE_SUCCESS,
                paymentRecordService.getPaymentRecordCycle(userIdx, paymentRecordIdx));
    }

}
