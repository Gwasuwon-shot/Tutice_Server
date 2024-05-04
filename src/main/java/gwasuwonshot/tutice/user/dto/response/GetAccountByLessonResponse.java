package gwasuwonshot.tutice.user.dto.response;

import gwasuwonshot.tutice.user.entity.Account;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetAccountByLessonResponse {
    private Long idx;
    private String bank;
    private String number;

    public static GetAccountByLessonResponse of(Account account) {
        return GetAccountByLessonResponse.builder()
                .idx(account.getIdx())
                .bank(account.getBank().getValue())
                .number(account.getNumber())
                .build();
    }
}
