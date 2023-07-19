package gwasuwonshot.tutice.user.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.external.firebase.service.FCMService;
import gwasuwonshot.tutice.lesson.exception.notfound.NotFoundLessonParentsException;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.exception.InvalidScheduleException;
import gwasuwonshot.tutice.schedule.exception.NotFoundScheduleException;
import gwasuwonshot.tutice.schedule.repository.ScheduleRepository;
import gwasuwonshot.tutice.schedule.service.ScheduleService;
import gwasuwonshot.tutice.user.dto.assembler.NotificationLogAssembler;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.exception.NotificationFailException;
import gwasuwonshot.tutice.user.exception.userException.InvalidRoleException;
import gwasuwonshot.tutice.user.exception.userException.NotAllowedNotificationException;
import gwasuwonshot.tutice.user.exception.userException.NotFoundUserException;
import gwasuwonshot.tutice.user.repository.NotificationLogRepository;
import gwasuwonshot.tutice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationLogRepository notificationLogRepository;
    private final ScheduleRepository scheduleRepository;
    private final FCMService fcmService;
    private final UserRepository userRepository;
    private final ScheduleService scheduleService;
    private final NotificationLogAssembler notificationLogAssembler;


    @Transactional
    public Long requestAttendanceNotification(Long userIdx, Long scheduleIdx){

//        유저가 존재하고 선생님인지 확인
        User teacher = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if(!teacher.isMatchedRole(Role.TEACHER)){
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }
//        들어온 스케쥴이 존재하는지, 스케쥴의 레슨이 유저와 연결되어있는지확인
        Schedule schedule = scheduleRepository.findById(scheduleIdx)
                .orElseThrow(()-> new NotFoundScheduleException(ErrorStatus.NOT_FOUND_SCHEDULE_EXCEPTION,ErrorStatus.NOT_FOUND_SCHEDULE_EXCEPTION.getMessage()));

        if(!schedule.getLesson().isMatchedTeacher(teacher)){
            throw new InvalidScheduleException(ErrorStatus.INVALID_SCHEDULE_EXCEPTION,ErrorStatus.INVALID_SCHEDULE_EXCEPTION.getMessage());
        }

//        레슨에 학부모가 연결되어있는지확인
        if(schedule.getLesson().getParents()==null){
            throw new NotFoundLessonParentsException(ErrorStatus.NOT_FOUND_LESSON_PARENTS_EXCEPTION,ErrorStatus.NOT_FOUND_LESSON_PARENTS_EXCEPTION.getMessage());
        }

        // 학부모가 알림을 허용했는지 확인
        if(schedule.getLesson().getParents().getDeviceToken()==null){
            throw new NotAllowedNotificationException(ErrorStatus.NOT_ALLOWED_NOTIFICATION_EXCEPTION,ErrorStatus.NOT_ALLOWED_NOTIFICATION_EXCEPTION.getMessage() );
        }

//        제목과 텍스트로 알림보내기
       // 제목 : OO 학생의 n회차 수업이 끝났어요.
         //       텍스트 : 나무를 통해 출결 현황을 확인해보세요!

        String title=schedule.getLesson().getStudentName()+" 학생의 "+scheduleService.getExpectedScheduleCount(schedule)+"회차 수업이 끝났어요.";
        String body="나무를 통해 출결 현황을 확인해보세요!";


        try {
            Response response =  fcmService.sendMessage(schedule.getLesson().getParents().getDeviceToken(), title, body);
        } catch (IOException e) {
            // 알림이 보내지지 않았을때
            throw new NotificationFailException(ErrorStatus.NOTIFICATION_FAIL_EXCEPTION,ErrorStatus.NOTIFICATION_FAIL_EXCEPTION.getMessage());
        }

        //  보내진게 맞으면  알림 기록하기
        notificationLogRepository.save(notificationLogAssembler.toEntity(schedule.getLesson().getParents(),title,body));

        return schedule.getIdx();

    }
}
