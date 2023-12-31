package gwasuwonshot.tutice.common.controller;

import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.dto.GetTodayDateResponse;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class CommonController {

    private final Environment env;

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
}
