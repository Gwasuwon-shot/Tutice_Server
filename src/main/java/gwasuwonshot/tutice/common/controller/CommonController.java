package gwasuwonshot.tutice.common.controller;

import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.dto.GetTodayDateResponse;
import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.validator.createLessonValid.CreateLessonValid;
import gwasuwonshot.tutice.common.resolver.validator.createLessonValid.CreateLessonValidator;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonRequest;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonRequestRegularSchedule;
import gwasuwonshot.tutice.user.exception.userException.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class CommonController {

    private final Environment env;
    private final CreateLessonValidator createLessonValidator;

    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(env.getActiveProfiles())
                .findFirst()
                .orElse("");
    }

    @GetMapping("/today")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetTodayDateResponse> getTodayDate() {
        return ApiResponse.success(SuccessStatus.GET_TODAY_DATE_SUCCESS, GetTodayDateResponse.of());
    }


    @GetMapping("/time/validation")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse validateTimeRange(@RequestParam("start-time") final String startTime,
                                         @RequestParam("end-time") final String endTime){
        if(!createLessonValidator.isValid(startTime,endTime)){
            throw new BasicException(ErrorStatus.INVALID_REGULAR_SCHEDULE_TIME, ErrorStatus.INVALID_REGULAR_SCHEDULE_TIME.getMessage());
        }
        return ApiResponse.success(SuccessStatus.VALIDATE_TIME_RANGE_SUCCESS);
    }
}
