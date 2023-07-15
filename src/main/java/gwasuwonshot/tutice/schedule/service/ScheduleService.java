package gwasuwonshot.tutice.schedule.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.schedule.dto.response.GetTodayScheduleByParentsResponseDto;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.repository.ScheduleRepository;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.exception.userException.InvalidRoleException;
import gwasuwonshot.tutice.user.exception.userException.NotFoundUserException;
import gwasuwonshot.tutice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final LessonRepository lessonRepository;

    public GetTodayScheduleByParentsResponseDto getTodayScheduleByParents(Long userIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 유저가 부모님인지 확인
        if(!user.isMatchedRole(Role.PARENTS)) throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        // 오늘의 수업 있는지 체크
        Date now = Date.valueOf(LocalDate.now());

        // 학부모-레슨리스트 -> 스케줄 중 해당 레슨 있는지 + 오늘 날짜인지
        // TODO 부모님 수업 리스트 양방향 매핑으로 수정하기
        List<Lesson> lessonList = lessonRepository.findAllByParentsIdx(userIdx);
        List<Schedule> todayScheduleList = scheduleRepository.findAllByDateAndLessonIn(now, lessonList);
        if(todayScheduleList == null) return GetTodayScheduleByParentsResponseDto.of(user.getName());
        return GetTodayScheduleByParentsResponseDto.ofTodaySchedule(user.getName(), now, todayScheduleList);
    }
}
