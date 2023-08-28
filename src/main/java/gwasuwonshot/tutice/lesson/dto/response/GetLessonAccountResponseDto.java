package gwasuwonshot.tutice.lesson.dto.response;

import gwasuwonshot.tutice.user.entity.Account;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetLessonAccountResponseDto {
    private Long idx;
    private String name;
    private String bank;
    private String number;

    public static GetLessonAccountResponseDto of(Account account) {
        return GetLessonAccountResponseDto.builder()
                .idx(account.getIdx())
                .name(account.getName())
                .bank(account.getBank())
                .number(account.getNumber())
                .build();
    }
}
