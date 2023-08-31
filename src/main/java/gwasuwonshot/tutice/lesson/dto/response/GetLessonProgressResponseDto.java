package gwasuwonshot.tutice.lesson.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetLessonProgressResponseDto {
    private Long idx;
    private Long count;
    private Long nowCount;
    private Long percent;

    public static GetLessonProgressResponseDto of(Long idx, Long count, Long nowCount, Long percent) {
        return GetLessonProgressResponseDto.builder()
                .idx(idx)
                .count(count)
                .nowCount(nowCount)
                .percent(percent)
                .build();
    }
}
