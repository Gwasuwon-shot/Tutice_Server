package gwasuwonshot.tutice.lesson.entity;

import gwasuwonshot.tutice.common.resolver.enumValue.EnumModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DayOfWeek implements EnumModel {
    MONDAY("DAY_OF_WEEK_MONDAY","월"),
    TUESDAY("DAY_OF_WEEK_TUESDAY","화"),
    WEDNESDAY("DAY_OF_WEEK_WEDNESDAY","수"),
    THURSDAY("DAY_OF_WEEK_THURSDAY","목"),
    FRIDAY("DAY_OF_WEEK_FRIDAY","금"),
    SATURDAY("DAY_OF_WEEK_SATURDAY","토"),
    SUNDAY("DAY_OF_WEEK_SUNDAY","일");




    private final String key;
    private final String value;

    public static DayOfWeek getDayOfWeekByValue(String value){
        return Arrays.stream(DayOfWeek.values())
                .filter(p -> p.getValue().equals(value))
                .findAny().orElseThrow();
    }
}
