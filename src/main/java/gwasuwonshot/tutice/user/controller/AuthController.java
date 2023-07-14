package gwasuwonshot.tutice.user.controller;

import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.user.dto.request.LocalLoginRequestDto;
import gwasuwonshot.tutice.user.dto.request.LocalSignUpRequestDto;
import gwasuwonshot.tutice.user.dto.response.LoginResponseDto;
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
    public ApiResponseDto<LoginResponseDto> localSignUp(@RequestBody @Valid final LocalSignUpRequestDto request) {
        return ApiResponseDto.success(SuccessStatus.SIGNUP_SUCCESS, userService.localSignUp(request));
    }

    @PostMapping("/local/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<LoginResponseDto> localLogin(@RequestBody @Valid final LocalLoginRequestDto request) {
        return ApiResponseDto.success(SuccessStatus.LOGIN_SUCCESS, userService.localLogin(request));
    }

}
