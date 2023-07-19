package gwasuwonshot.tutice.user.entity;

import gwasuwonshot.tutice.common.resolver.enumValue.EnumModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;


@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor
public enum NotificationConstant{
    //TODO : 리팩 진짜 꼭 해라!
    ATTENDANCE_IMMEDIATE_CHECK("출결체크할 시간이에요.","수업이 종료되었네요! 출결을 입력해주세요."),
    ATTENDANCE_LATE_CHECK("아직 출결체크가 되지 않았어요!","출결체크, 더 잊기 전에 지금 체크해보세요."),
    REQUEST_ATTENDANCE(null,"나무를 통해 출결 현황을 확인해보세요!"),
    REQUEST_PAYMENT_RECORD(null,null);

    private final String title;
    private final String content;

   //메소드 만들기
   public static String getAttendanceImmediateCheckTitle(){
       return NotificationConstant.ATTENDANCE_IMMEDIATE_CHECK.getTitle();
   }
   public static String getAttendanceImmediateCheckContent(){
        return NotificationConstant.ATTENDANCE_IMMEDIATE_CHECK.getContent();
    }

    public static String getAttendanceLateCheckTitle(){
        return NotificationConstant.ATTENDANCE_LATE_CHECK.getTitle();
    }

    public static String getAttendanceLateCheckContent(){
        return NotificationConstant.ATTENDANCE_LATE_CHECK.getContent();
    }

    public static String requestAttendanceTitle(String studentName, Integer expectedCount){
        //OO 학생의 n회차 수업이 끝났어요.
        return studentName+" 학생의 "+expectedCount+"회차 수업이 끝났어요.";
    }

    public static String requestAttendanceContent(){

        return NotificationConstant.REQUEST_ATTENDANCE.getContent();
    }

    public static String requestPaymentRecordTitle(String studentName){
        // OO 학생의 모든 수업회차가 끝났어요.
        return studentName+" 학생의 모든 수업회차가 끝났어요.";
    }

    public static String requestPaymentRecordContent(String teacherName){
        // 수고한 OOO 선생님께 수업비 입금, 잊지 마세요!
        return "수고한 "+teacherName+" 선생님께 수업비 입금, 잊지 마세요!";
    }



}
