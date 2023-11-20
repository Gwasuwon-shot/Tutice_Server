package gwasuwonshot.tutice.lesson.dto.response;

import gwasuwonshot.tutice.user.entity.Account;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetLessonAccountResponse {
    private Long idx;
    private String name;
    private String bank;
    private String number;

    public static GetLessonAccountResponse of(Account account) {
        return GetLessonAccountResponse.builder()
                .idx(account.getIdx())
                .name(account.getName())
                .bank(account.getBank())
                .number(account.getNumber())
                .build();
    }
}
