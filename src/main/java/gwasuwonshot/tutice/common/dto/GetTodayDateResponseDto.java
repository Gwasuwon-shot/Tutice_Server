package gwasuwonshot.tutice.common.dto;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetTodayDateResponseDto {
    private String date;
    private String dayOfWeek;

    public static GetTodayDateResponseDto of(LocalDate now) {
        return new GetTodayDateResponseDto(
                DateAndTimeConvert.localDateConvertString(now),
                DateAndTimeConvert.localDateConvertDayOfWeek(now));
    }
}
