package gwasuwonshot.tutice.lesson.entity;

import gwasuwonshot.tutice.common.resolver.enumValue.EnumModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DayOfWeek implements EnumModel {
    MONDAY("DAY_OF_WEEK_MONDAY","월",1L),
    TUESDAY("DAY_OF_WEEK_TUESDAY","화",2L),
    WEDNESDAY("DAY_OF_WEEK_WEDNESDAY","수",3L),
    THURSDAY("DAY_OF_WEEK_THURSDAY","목",4L),
    FRIDAY("DAY_OF_WEEK_FRIDAY","금",5L),
    SATURDAY("DAY_OF_WEEK_SATURDAY","토",6L),
    SUNDAY("DAY_OF_WEEK_SUNDAY","일",7L);




    private final String key;
    private final String value;
    private final Long index; // java.time.DayOfWeek와 호환을 위해 만듬

    public static DayOfWeek getDayOfWeekByValue(String value){
        return Arrays.stream(DayOfWeek.values())
                .filter(p -> p.getValue().equals(value))
                .findAny().orElseThrow();
    }

    public static Long getIndexByValue(String value){
        return Arrays.stream(DayOfWeek.values())
                .filter(p -> p.getValue().equals(value))
                .map(DayOfWeek::getIndex)
                .findAny()
                .orElseThrow();
    }
}
