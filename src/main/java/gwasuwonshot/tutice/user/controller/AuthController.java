package gwasuwonshot.tutice.user.controller;

import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.config.sms.SmsService;
import gwasuwonshot.tutice.external.naver.NaverService;
import gwasuwonshot.tutice.user.dto.request.*;
import gwasuwonshot.tutice.user.dto.response.GetNaverProfileInfoResponse;
import gwasuwonshot.tutice.user.dto.response.LoginResponse;
import gwasuwonshot.tutice.user.dto.response.ReissueTokenResponse;
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
    private final NaverService naverService;

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

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<LoginResponse> loginAndTempSignUp(@RequestBody @Valid final LoginRequest request) {
        if(userService.isUser(request)) return ApiResponse.success(SuccessStatus.LOGIN_SUCCESS, userService.login(request));
        else return ApiResponse.error(ErrorStatus.NOT_FOUND_USER_EXCEPTION, userService.tempSignUp(request));
    }

    @PostMapping("/phone/validation")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse validatePhone(@RequestBody @Valid final ValidatePhoneRequest request) {
        smsService.validatePhone(request);
        return ApiResponse.success(SuccessStatus.VALIDATE_PHONE_SUCCESS);
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ReissueTokenResponse> reissueToken(@RequestBody @Valid final ReissueTokenRequest request) {
        return ApiResponse.success(SuccessStatus.REISSUE_TOKEN_SUCCESS, userService.reissueToken(request));
    }

    @PostMapping("/naver")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetNaverProfileInfoResponse> getNaverProfileInfo(@RequestBody @Valid final GetNaverProfileInfoRequest request) {
        return ApiResponse.success(SuccessStatus.GET_NAVER_PROFILE_INFO_SUCCESS, naverService.getNaverProfileInfo(request));
    }

}
