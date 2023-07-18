package gwasuwonshot.tutice.lesson.controller;


import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.lesson.dto.request.UpdateLessonParentsRequestDto;
import gwasuwonshot.tutice.lesson.dto.request.UpdatePaymentRecordRequestDto;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonRequestDto;
import gwasuwonshot.tutice.lesson.dto.response.CreateLessonResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordView.GetPaymentRecordViewResponseDto;
import gwasuwonshot.tutice.lesson.service.PaymentRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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


    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetPaymentRecordViewResponseDto> getPaymentRecordView(
            @UserIdx final Long userIdx,
            @RequestParam final Long lessonIdx,
            @RequestParam String paymentRecordIdx) { //ToDo : 원래는 400 에러 커스텀해서 안들어올때도 되게해야햐지만...


        System.out.println("레슨아이디"+lessonIdx);
        System.out.println("입금기록아이디"+paymentRecordIdx);

        if(paymentRecordIdx.equals("null")){
            paymentRecordIdx=null;

            return ApiResponseDto.success(SuccessStatus.GET_PAYMENT_RECORD_POST_VIEW_SUCCESS,
                    GetPaymentRecordViewResponseDto.of(paymentRecordService.getPaymentRecordView(userIdx, lessonIdx,null),
                            DateAndTimeConvert.nowLocalDateConvertString()));
        }

        return ApiResponseDto.success(SuccessStatus.GET_PAYMENT_RECORD_POST_VIEW_SUCCESS,
                GetPaymentRecordViewResponseDto.of(paymentRecordService.getPaymentRecordView(userIdx, lessonIdx,Long.parseLong(paymentRecordIdx)),
                        DateAndTimeConvert.nowLocalDateConvertString()));


    }

}
