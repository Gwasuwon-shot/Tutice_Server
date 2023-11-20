package gwasuwonshot.tutice.lesson.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetLessonProgressResponse {
    private Long idx;
    private Long count;
    private Long nowCount;
    private Long percent;

    public static GetLessonProgressResponse of(Long idx, Long count, Long nowCount, Long percent) {
        return GetLessonProgressResponse.builder()
                .idx(idx)
                .count(count)
                .nowCount(nowCount)
                .percent(percent)
                .build();
    }
}
