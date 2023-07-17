package gwasuwonshot.tutice.lesson.dto.request.createLesson;

import gwasuwonshot.tutice.common.resolver.enumValue.Enum;
import gwasuwonshot.tutice.lesson.entity.DayOfWeek;
import gwasuwonshot.tutice.lesson.entity.Payment;
import gwasuwonshot.tutice.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateLessonRequestRegularSchedule {
    @NotBlank(message = "요일이 없습니다.")
    @Schema(description = "요일")
    // TODO :  enum validation으로 변경
    @Enum(enumClass = DayOfWeek.class, ignoreCase = true, message ="요일은 월,화,수,목,금,토,일 중 하나여야 합니다.")
    private String dayOfWeek;

    @NotBlank(message = "시작 시간이 없습니다.")
    @Schema(description = "시작 시간")
    // TODO :  타임 추후 validate 확실하게

//    @Pattern(//문제
//            regexp="/(0[1-9]|1[0-9]|2[0-4]):(0[0-9]|[0-5][0-9]):00$/", //시간정보
//            message = "시작 시간의 형태는 HH:mm:00의 형태여야합니다."
//    )
    private String startTime;

    @NotBlank(message = "종료 시간이 없습니다.")
    @Schema(description = "종료 시간")
    // TODO :  타임 추후 validate 확실하게
//    @Pattern(
//            regexp="/(0[1-9]|1[0-9]|2[0-4]):(0[0-9]|[0-5][0-9]):00$/", //시간정보
//            message = "종료 시간의 형태는 HH:mm:00의 형태여야합니다."
//    )
    private String endTime;
}

