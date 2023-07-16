package gwasuwonshot.tutice.schedule.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.schedule.dto.response.GetScheduleByUserResponseDto;
import gwasuwonshot.tutice.schedule.dto.response.GetTodayScheduleByParentsResponseDto;
import gwasuwonshot.tutice.schedule.dto.response.ScheduleByDate;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.repository.ScheduleRepository;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.exception.userException.InvalidRoleException;
import gwasuwonshot.tutice.user.exception.userException.NotFoundUserException;
import gwasuwonshot.tutice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
        LocalDate now = LocalDate.now();

        // 학부모-레슨리스트 -> 스케줄 중 해당 레슨 있는지 + 오늘 날짜인지
        // TODO 부모님 수업 리스트 양방향 매핑으로 수정하기
        List<Lesson> lessonList = lessonRepository.findAllByParentsIdx(userIdx);
        List<Schedule> todayScheduleList = scheduleRepository.findAllByDateAndLessonIn(now, lessonList);
        if(todayScheduleList == null) return GetTodayScheduleByParentsResponseDto.of(user.getName());
        return GetTodayScheduleByParentsResponseDto.ofTodaySchedule(user.getName(), now, todayScheduleList);
    }

    public GetScheduleByUserResponseDto getScheduleByUser(Long userIdx, String month) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        // month(YYYY-MM) 바탕 한 달 기준 설정
        LocalDate standardDate = DateAndTimeConvert.stringConvertLocalDate(month + "-01");
        LocalDate startDate = standardDate.withDayOfMonth(1);
        LocalDate endDate = standardDate.withDayOfMonth(standardDate.lengthOfMonth());

        // 유저 역할 따라 스케줄 가져오기
        List<Lesson> lessonList;
        if (user.isMatchedRole(Role.PARENTS)) {
            lessonList = lessonRepository.findAllByParentsIdx(userIdx);
        } else if (user.isMatchedRole(Role.TEACHER)) {
            lessonList = lessonRepository.findAllByTeacherIdx(userIdx);
        } else {
            return null;
        }

        // TODO 최적화 코드 생각해서 리팩하기 (날짜 별 스케줄 묶기)
        List<Schedule> scheduleList = scheduleRepository.findAllByDateBetweenAndLessonInOrderByDate(startDate, endDate, lessonList);
        if (scheduleList.isEmpty()) {
            return GetScheduleByUserResponseDto.of();
        }
        else {
            // 날짜 별 스케줄 리스트
            LocalDate scheduleDate = scheduleList.get(0).getDate();
            List<Schedule> scheduleListOfDate = new ArrayList<>();
            List<ScheduleByDate> dailyScheduleList = new ArrayList<>();

            for (Schedule schedule : scheduleList) {
                if (!scheduleDate.isEqual(schedule.getDate())) {
                    dailyScheduleList.add(ScheduleByDate.of(DateAndTimeConvert.localDateConvertString(scheduleDate), DateAndTimeConvert.localDateConvertDayOfWeek(scheduleDate), scheduleListOfDate));
                    scheduleDate = schedule.getDate();
                    scheduleListOfDate.clear();
                }
                scheduleListOfDate.add(schedule);
            }
            dailyScheduleList.add(ScheduleByDate.of(DateAndTimeConvert.localDateConvertString(scheduleDate), DateAndTimeConvert.localDateConvertDayOfWeek(scheduleDate), scheduleListOfDate));

            return GetScheduleByUserResponseDto.ofDailySchedule(dailyScheduleList);
        }
    }
}
