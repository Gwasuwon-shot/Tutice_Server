package gwasuwonshot.tutice.user.controller;

import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.user.dto.request.CheckDuplicationEmailRequest;
import gwasuwonshot.tutice.user.dto.request.LocalLoginRequest;
import gwasuwonshot.tutice.user.dto.request.LocalSignUpRequest;
import gwasuwonshot.tutice.user.dto.request.ReissueTokenRequest;
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

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ReissueTokenResponse> reissueToken(@RequestBody @Valid final ReissueTokenRequest request) {
        return ApiResponse.success(SuccessStatus.REISSUE_TOKEN_SUCCESS, userService.reissueToken(request));
    }

}
