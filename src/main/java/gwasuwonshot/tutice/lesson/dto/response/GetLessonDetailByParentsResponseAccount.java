package gwasuwonshot.tutice.lesson.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonDetailByParentsResponseAccount {
    private String name;
    private String bank;
    private String number;

    public static GetLessonDetailByParentsResponseAccount of(String name, String bank, String number) {
        return new GetLessonDetailByParentsResponseAccount(name, bank,number);
    }
}
