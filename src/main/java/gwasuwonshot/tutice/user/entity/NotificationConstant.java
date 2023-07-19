package gwasuwonshot.tutice.user.entity;

import gwasuwonshot.tutice.common.resolver.enumValue.EnumModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum NotificationConstant{
    ATTENDANCE_IMMEDIATE_CHECK("출결 체크할 시간이에요.","수업이 종료되었네요! 출결을 입력해주세요."),
    ATTENDANCE_LATE_CHECK("아직 출결체크가 되지 않았어요!"," 출결체크, 더 잊기 전에 지금 체크해보세요."),
    REQUEST_ATTENDANCE("아"," 요."),
    REQUEST_PAYMENT_RECORD("아직 출결되지 않았어요!"," 출결체크, 크해보세요.");



    private final String title;
    private final String content;

   //메소드 만들기

}
