package gwasuwonshot.tutice.user.controller;

import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonAccountResponse;
import gwasuwonshot.tutice.user.dto.request.UpdateUserDeviceTokenRequest;
import gwasuwonshot.tutice.user.dto.response.GetUserNameResponse;
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
    public ApiResponse updateUserDeviceToken(@UserIdx final Long userIdx,
                                             @RequestBody @Valid final UpdateUserDeviceTokenRequest request) {
        userService.updateUserDeviceToken(userIdx, request);
        return ApiResponse.success(SuccessStatus.UPDATE_DEVICE_TOKEN_SUCCESS);
    }

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetUserNameResponse> getUserName(@UserIdx final Long userIdx) {
        return ApiResponse.success(SuccessStatus.GET_USER_NAME_SUCCESS, userService.getUserName(userIdx));
    }

    @GetMapping("/account/lesson/{lessonIdx}")
    @ResponseStatus(HttpStatus.OK)
    // TODO url만 변경 dto, 함수명 변경 필요
    public ApiResponse<GetLessonAccountResponse> getAccountByLesson(@UserIdx final Long userIdx,
                                                                    @PathVariable final Long lessonIdx) {
        return ApiResponse.success(SuccessStatus.GET_LESSON_ACCOUNT_SUCCESS, userService.getAccountByLesson(userIdx, lessonIdx));
    }

    @PatchMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse logout(@UserIdx final Long userIdx) {
        userService.logout(userIdx);
        return ApiResponse.success(SuccessStatus.LOGOUT_SUCCESS);
    }
}
