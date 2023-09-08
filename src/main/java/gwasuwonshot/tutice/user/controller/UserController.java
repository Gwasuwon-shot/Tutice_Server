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

    @PatchMapping("/device-token")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updateUserDeviceToken(@UserIdx final Long userIdx,
                                                @RequestBody @Valid final UpdateUserDeviceTokenRequestDto request) {
        userService.updateUserDeviceToken(userIdx, request);
        return ApiResponseDto.success(SuccessStatus.UPDATE_DEVICE_TOKEN_SUCCESS);
    }

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetUserNameResponseDto> getUserName(@UserIdx final Long userIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_USER_NAME_SUCCESS, userService.getUserName(userIdx));
    }

    @GetMapping("/account/lesson/{lessonIdx}")
    @ResponseStatus(HttpStatus.OK)
    // TODO url만 변경 dto, 함수명 변경 필요
    public ApiResponseDto<GetLessonAccountResponseDto> getAccountByLesson(@UserIdx final Long userIdx,
                                                                        @PathVariable final Long lessonIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_LESSON_ACCOUNT_SUCCESS, userService.getAccountByLesson(userIdx, lessonIdx));
    }
}
