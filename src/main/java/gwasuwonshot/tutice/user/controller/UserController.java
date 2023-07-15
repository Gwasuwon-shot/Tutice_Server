package gwasuwonshot.tutice.user.controller;

import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.user.dto.request.UpdateUserDeviceTokenRequestDto;
import gwasuwonshot.tutice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PatchMapping("/device-token")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updateUserDeviceToken(@UserIdx final Long userIdx,
                                                @RequestBody @Valid final UpdateUserDeviceTokenRequestDto request) {
        userService.updateUserDeviceToken(userIdx, request);
        return ApiResponseDto.success(SuccessStatus.UPDATE_DEVICE_TOKEN_SUCCESS);
    }
}
