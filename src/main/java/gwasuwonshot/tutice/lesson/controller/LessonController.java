package gwasuwonshot.tutice.lesson.controller;

import gwasuwonshot.tutice.common.resolver.userId.UserId;
import gwasuwonshot.tutice.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lesson")
public class LessonController {

    private final LessonService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<List<RandomTourResponseDto>> createLesosn(
            @UserId final Long userId,
            @RequestBody @Valid final UserRequestDto request ) {

        // header Location에 관한 에러처리
        if (!"paris".equals(Location) && !"global".equals(Location)){
            throw new BusinessException(Error.REQUEST_VALIDATION_EXCEPTION);
        }

        List<RandomTourResponseDto> randomTourListResponseDto= tourService.getRandomTourList(Location);

        return ApiResponseDto.success(Success.GET_RANDOM_TOURLIST_SUCCESS,randomTourListResponseDto);

    }

}
