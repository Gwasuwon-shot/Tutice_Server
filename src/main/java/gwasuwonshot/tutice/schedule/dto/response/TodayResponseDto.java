package gwasuwonshot.tutice.schedule.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TodayResponseDto {
    private String date;
    private String dayOfWeek;

    public static TodayResponseDto of(String date, String dayOfWeek) {
        return TodayResponseDto.builder()
                .date(date)
                .dayOfWeek(dayOfWeek)
                .build();
    }
}
