package gwasuwonshot.tutice.schedule.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.schedule.dto.response.GetScheduleByUserResponseDto;
import gwasuwonshot.tutice.schedule.dto.response.GetTodayScheduleByParentsResponseDto;
import gwasuwonshot.tutice.schedule.dto.response.GetTodayScheduleByTeacherResponseDto;
import gwasuwonshot.tutice.schedule.dto.response.ScheduleByDate;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.entity.ScheduleStatus;
import gwasuwonshot.tutice.schedule.repository.ScheduleRepository;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.exception.userException.InvalidRoleException;
import gwasuwonshot.tutice.user.exception.userException.NotFoundUserException;
import gwasuwonshot.tutice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final Integer BEFORE_SCHEDULE = 1;
    private final Integer ONGOING_SCHEDULE = 2;
    private final Integer AFTER_SCHEDULE = 3;

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

    // TODO 리팩.. 리팩.. 깔깔
    public GetTodayScheduleByTeacherResponseDto getTodayScheduleByTeacher(Long userIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        // 유저가 선생님인지 확인
        if(!user.isMatchedRole(Role.TEACHER)) throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());

        // 오늘의 수업 있는지 체크
        LocalDate now = LocalDate.now();
        List<Lesson> lessonList = user.getLessonList();
        List<Schedule> todayScheduleList = scheduleRepository.findAllByDateAndLessonInOrderByStartTime(now, lessonList);

        // 오늘의 수업 유무
        if(todayScheduleList.isEmpty()) {
            return GetTodayScheduleByTeacherResponseDto.of(user.getName());
        }
        LocalTime nowTime = LocalTime.now();
        Integer timeStatus;

        // 오늘의 수업 여러개일 때
        int i=0;
        int size = todayScheduleList.size();
        for(Schedule schedule : todayScheduleList) {
            Integer scheduleCount = getScheduleCount(schedule);
            i++;
            // 수업 전
            if(nowTime.isBefore(schedule.getStartTime())) {
                timeStatus=BEFORE_SCHEDULE;
                // case 2
                return GetTodayScheduleByTeacherResponseDto.ofTodaySchedule(user.getName(), schedule, timeStatus, scheduleCount);
            }
            // 수업 중
            else if(nowTime.equals(schedule.getStartTime()) || nowTime.isBefore(schedule.getEndTime())) {
                timeStatus=ONGOING_SCHEDULE;
                // 수업 체크 여부
                if(schedule.getStatus()==ScheduleStatus.NO_STATUS) {
                    // case 3
                    return GetTodayScheduleByTeacherResponseDto.ofTodaySchedule(user.getName(), schedule, timeStatus, scheduleCount);
                } else {
                    // 다음 수업 여부
                    if(i==todayScheduleList.size()) {
                        // 수업X
                        return GetTodayScheduleByTeacherResponseDto.ofCompletedAttendance(user.getName());
                    } else {
                        continue;
                    }
                }
            }
            // 수업 끝
            else {
                timeStatus=AFTER_SCHEDULE;
                // 수업 체크 여부
                if(schedule.getStatus()==ScheduleStatus.NO_STATUS) {
                    // 체크X
                    // 다음 수업 여부
                    if(i==todayScheduleList.size()) {
                        // 수업X
                        return GetTodayScheduleByTeacherResponseDto.ofTodaySchedule(user.getName(), schedule, timeStatus, scheduleCount);
                    } else {
                        // 수업O
                        // 다음 수업 시작 여부
                        if(nowTime.isBefore(todayScheduleList.get(i).getStartTime())) {
                            // 시작X
                            return GetTodayScheduleByTeacherResponseDto.ofTodaySchedule(user.getName(), schedule, timeStatus, scheduleCount);
                        } else {
                            // 시작O
                            continue;
                        }
                    }
                } else {
                    // 체크O
                    if(i==todayScheduleList.size()) {
                        // 수업X
                        // 1개이면
                        if(todayScheduleList.size()==1) {
                            return GetTodayScheduleByTeacherResponseDto.ofCompletedAttendance(user.getName());
                        }
                        break;
                    } else {
                        // 수업O
                        continue;
                    }
                }
            }
        }

        // 전체 체크 안한 것 있는지 확인
        List<Schedule> scheduleList = scheduleRepository.findAllByDateAndStatusAndLessonInOrderByStartTimeDesc(now, ScheduleStatus.NO_STATUS, lessonList);
        if(!scheduleList.isEmpty()) {
            Schedule schedule = scheduleList.get(0);
            return GetTodayScheduleByTeacherResponseDto.ofTodaySchedule(user.getName(), schedule, AFTER_SCHEDULE, getScheduleCount(schedule));
        }
        return null;
    }

    // 현재 스케줄이 몇 회차인지 구하는 로직
    private Integer getScheduleCount(Schedule schedule) {
        Long lessonIdx = schedule.getLesson().getIdx();
        List<ScheduleStatus> attendanceStatusList = new ArrayList<>((Arrays.asList(ScheduleStatus.ATTENDANCE, ScheduleStatus.ABSENCE)));
        Integer count = scheduleRepository.countByLesson_IdxAndStatusIn(lessonIdx, attendanceStatusList);
        return count+1;
    }
}
