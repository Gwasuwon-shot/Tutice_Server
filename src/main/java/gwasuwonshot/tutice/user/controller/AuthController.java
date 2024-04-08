package gwasuwonshot.tutice.user.controller;

import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.config.sms.SmsService;
import gwasuwonshot.tutice.user.dto.request.*;
import gwasuwonshot.tutice.user.dto.response.LoginResponse;
import gwasuwonshot.tutice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final SmsService smsService;

    @PostMapping("/local/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<LoginResponse> localSignUp(@RequestBody @Valid final LocalSignUpRequest request) {
        return ApiResponse.success(SuccessStatus.SIGNUP_SUCCESS, userService.localSignUp(request));
    }

    @PostMapping("/local/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<LoginResponse> localLogin(@RequestBody @Valid final LocalLoginRequest request) {
        return ApiResponse.success(SuccessStatus.LOGIN_SUCCESS, userService.localLogin(request));
    }

    @PostMapping("/email/duplication")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse checkDuplicationEmail(@RequestBody @Valid final CheckDuplicationEmailRequest request) {
        userService.checkDuplicationEmail(request);
        return ApiResponse.success(SuccessStatus.CHECK_DUPLICATION_EMAIL_SUCCESS);
    }

    @PostMapping("/phone")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse sendValidationNumber(@RequestBody @Valid final SendValidationNumberRequest request) {
        userService.sendValidationNumber(request);
        return ApiResponse.success(SuccessStatus.SEND_VALIDATION_NUMBER_SUCCESS);
    }

    @PostMapping("/phone/validation")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse validatePhone(@RequestBody @Valid final ValidatePhoneRequest request) {
        smsService.validatePhone(request);
        return ApiResponse.success(SuccessStatus.VALIDATE_PHONE_SUCCESS);
    }

}
