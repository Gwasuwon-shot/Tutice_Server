package gwasuwonshot.tutice.user.controller;

import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
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
    public ApiResponse<LoginResponseDto> localSignUp(@RequestBody @Valid final LocalSignUpRequestDto request) {
        return ApiResponse.success(SuccessStatus.SIGNUP_SUCCESS, userService.localSignUp(request));
    }

}
