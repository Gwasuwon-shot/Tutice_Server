package gwasuwonshot.tutice.schedule.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DateAndDayOfWeek {
    private String date;
    private String dayOfWeek;

    public static DateAndDayOfWeek of(String date, String dayOfWeek) {
        return DateAndDayOfWeek.builder()
                .date(date)
                .dayOfWeek(dayOfWeek)
                .build();
    }
}
