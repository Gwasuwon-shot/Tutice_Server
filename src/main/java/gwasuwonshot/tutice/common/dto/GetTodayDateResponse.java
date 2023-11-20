package gwasuwonshot.tutice.common.dto;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetTodayDateResponse {
    private String date;
    private String dayOfWeek;

    public static GetTodayDateResponse of() {
        return new GetTodayDateResponse(
                DateAndTimeConvert.nowLocalDateConvertString(),
                DateAndTimeConvert.nowLocalDateConvertDayOfWeek());
    }
}
