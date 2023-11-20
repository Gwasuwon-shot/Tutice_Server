package gwasuwonshot.tutice.lesson.controller;


import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.lesson.dto.request.UpdatePaymentRecordRequest;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordByLesson.GetPaymentRecordByLessonResponse;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordCycle.GetPaymentRecordCycleResponse;
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

    @GetMapping("/lesson/{lessonIdx}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<GetPaymentRecordByLessonResponse>> getPaymentRecordByLesson(
            @UserIdx final Long userIdx,
            @PathVariable final Long lessonIdx
    ) {
        return ApiResponse.success(SuccessStatus.GET_PAYMENT_RECORD_BY_LESSON_SUCCESS,
                paymentRecordService.getPaymentRecordByLesson(userIdx, lessonIdx));
    }

    @GetMapping("/{paymentRecordIdx}/cycle")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetPaymentRecordCycleResponse> getPaymentRecordCycle(
            @UserIdx final Long userIdx,
            @PathVariable final Long paymentRecordIdx) {
        return ApiResponse.success(SuccessStatus.GET_PAYMENT_RECORD_CYCLE_SUCCESS,
                paymentRecordService.getPaymentRecordCycle(userIdx, paymentRecordIdx));
    }

    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse updatePaymentRecord(
            @UserIdx final Long userIdx,
            @Valid @RequestBody final UpdatePaymentRecordRequest request) {

        paymentRecordService.updatePaymentRecord(userIdx, request.getPaymentRecordIdx(),DateAndTimeConvert.stringConvertLocalDate(request.getPaymentDate()));

        return ApiResponse.success(SuccessStatus.UPDATE_PAYMENT_RECORD_SUCCESS);

    }
}
