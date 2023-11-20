package gwasuwonshot.tutice.user.controller;

import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonAccountResponseDto;
import gwasuwonshot.tutice.user.dto.request.UpdateUserDeviceTokenRequestDto;
import gwasuwonshot.tutice.user.dto.response.GetUserNameResponseDto;
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


    @GetMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetUserNameResponseDto> getUserName(@UserIdx final Long userIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_USER_NAME_SUCCESS,
                userService.getUserName(userIdx));
    }

    @GetMapping("/account/lesson/{lessonIdx}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonAccountResponseDto> getAccountByLesson(@UserIdx final Long userIdx,
                                                                        @PathVariable final Long lessonIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_ACCOUNT_BY_LESSON_SUCCESS,
                userService.getAccountByLesson(userIdx, lessonIdx));
    }

    @PatchMapping("/device-token")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updateUserDeviceToken(@UserIdx final Long userIdx,
                                                @RequestBody @Valid final UpdateUserDeviceTokenRequestDto request) {
        userService.updateUserDeviceToken(userIdx, request);
        return ApiResponseDto.success(SuccessStatus.UPDATE_DEVICE_TOKEN_SUCCESS);
    }

    @PatchMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto logout(@UserIdx final Long userIdx) {
        userService.logout(userIdx);
        return ApiResponseDto.success(SuccessStatus.LOGOUT_SUCCESS);
    }


}
