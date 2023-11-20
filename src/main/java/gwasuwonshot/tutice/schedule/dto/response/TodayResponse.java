package gwasuwonshot.tutice.schedule.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TodayResponse {
    private String date;
    private String dayOfWeek;

    public static TodayResponse of(String date, String dayOfWeek) {
        return TodayResponse.builder()
                .date(date)
                .dayOfWeek(dayOfWeek)
                .build();
    }
}
