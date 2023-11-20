package gwasuwonshot.tutice.schedule.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.external.firebase.service.FCMService;
import gwasuwonshot.tutice.lesson.dto.response.getLessonSchedule.GetLessonScheduleResponseDto;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.RegularSchedule;
import gwasuwonshot.tutice.lesson.exception.invalid.InvalidLessonException;
import gwasuwonshot.tutice.lesson.exception.notfound.NotFoundLessonException;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.schedule.dto.request.GetTemporaryScheduleRequestDto;
import gwasuwonshot.tutice.schedule.dto.request.UpdateScheduleAttendanceRequestDto;
import gwasuwonshot.tutice.schedule.dto.request.UpdateScheduleRequestDto;
import gwasuwonshot.tutice.schedule.dto.response.*;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.entity.ScheduleStatus;
import gwasuwonshot.tutice.schedule.exception.*;
import gwasuwonshot.tutice.schedule.repository.ScheduleRepository;
import gwasuwonshot.tutice.user.entity.NotificationConstant;
import gwasuwonshot.tutice.user.entity.NotificationLog;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.exception.userException.InvalidRoleException;
import gwasuwonshot.tutice.user.exception.userException.NotFoundUserException;
import gwasuwonshot.tutice.user.repository.NotificationLogRepository;
import gwasuwonshot.tutice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final Integer BEFORE_SCHEDULE = 1;
    private final Integer ONGOING_SCHEDULE = 2;
    private final Integer AFTER_SCHEDULE = 3;

    private final FCMService fcmService;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final LessonRepository lessonRepository;
    private final NotificationLogRepository notificationLogRepository;

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
        List<Lesson> lessonList = lessonRepository.findAllByParentsIdxAndIsFinished(userIdx,false);
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
            lessonList = lessonRepository.findAllByParentsIdxAndIsFinished(userIdx,false);
        } else if (user.isMatchedRole(Role.TEACHER)) {
            lessonList = lessonRepository.findAllByTeacherIdxAndIsFinished(userIdx,false);
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
        List<Lesson> lessonList = lessonRepository.findAllByTeacherIdxAndIsFinished(userIdx,false);
        List<Schedule> todayScheduleList = scheduleRepository.findAllByDateAndLessonInOrderByStartTime(now, lessonList);

        // 오늘의 수업 유무
        if(todayScheduleList.isEmpty()) return null;
        LocalTime nowTime = LocalTime.now();
        Integer timeStatus;

        // 오늘의 수업 여러개일 때
        int i=0;
        for(Schedule schedule : todayScheduleList) {
            Integer expectedCount = getExpectedScheduleCount(schedule);
            i++;
            // 수업 전
            if(nowTime.isBefore(schedule.getStartTime())) {
                timeStatus=BEFORE_SCHEDULE;
                // case 2
                return GetTodayScheduleByTeacherResponseDto.of(schedule, timeStatus, expectedCount);
            }
            // 수업 중
            else if(nowTime.equals(schedule.getStartTime()) || nowTime.isBefore(schedule.getEndTime())) {
                timeStatus=ONGOING_SCHEDULE;
                // 수업 체크 여부
                if(schedule.getStatus()==ScheduleStatus.NO_STATUS) {
                    // case 3
                    return GetTodayScheduleByTeacherResponseDto.of(schedule, timeStatus, expectedCount);
                } else {
                    // 다음 수업 여부
                    if(i==todayScheduleList.size()) {
                        // 수업X
                        return null;
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
                        return GetTodayScheduleByTeacherResponseDto.of(schedule, timeStatus, expectedCount);
                    } else {
                        // 수업O
                        // 다음 수업 시작 여부
                        if(nowTime.isBefore(todayScheduleList.get(i).getStartTime())) {
                            // 시작X
                            return GetTodayScheduleByTeacherResponseDto.of(schedule, timeStatus, expectedCount);
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
                            return null;
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
            return GetTodayScheduleByTeacherResponseDto.of(schedule, AFTER_SCHEDULE, getExpectedScheduleCount(schedule));
        }
        return null;
    }

    // 현재 스케줄로 기대 회차 구하기
    public Integer getExpectedScheduleCount(Schedule schedule) {
        // 스케줄 리스트 중 date, startTime 오름차순 정렬 후, 인덱스 찾기
        Sort sort = Sort.by(Sort.Order.asc("date"), Sort.Order.asc("startTime"));
        List<Schedule> scheduleList = scheduleRepository.findAllByLessonAndCycleAndStatusNot(schedule.getLesson(), schedule.getCycle(), ScheduleStatus.CANCEL, sort);
        int index = scheduleList.indexOf(schedule);
        return index+1;
    }

    // TODO : 단일스케쥴진짜회차정보: 파라미터로 들어오는 스케줄이 이 스케쥴 사이클에서 몇 회차인지 구하는 로직(스케쥴의 상태는 출석 OR 결석만 가능) 메소드 필요해지면 만들기



    public GetMissingAttendanceScheduleResponseDto getMissingAttendanceScheduleByTeacher(Long userIdx) {
        // 유저 존재 여부
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        List<Lesson> lessonList = lessonRepository.findAllByTeacherIdxAndIsFinished(userIdx,false);

        // 오늘 수업 시작&끝 체크 안 한 것
        List<Schedule> missingScheduleList = scheduleRepository.findAllByStatusAndDateAndStartTimeIsBeforeAndLessonIn(ScheduleStatus.NO_STATUS, LocalDate.now(), LocalTime.now(), lessonList);
        // 오늘 이전 체크 안 한 것
        List<Schedule> afterTodayMissingScheduleList = scheduleRepository.findAllByStatusAndDateIsBeforeAndLessonInOrderByDate(ScheduleStatus.NO_STATUS, LocalDate.now(), lessonList);
        missingScheduleList.addAll(afterTodayMissingScheduleList);
        missingScheduleList.sort(Comparator.comparing(Schedule::getDate));

        // 누락 수업 없는 경우
        if(missingScheduleList.isEmpty()) return GetMissingAttendanceScheduleResponseDto.of();

        // 누락 수업 시간별 묶기
        List<Schedule> scheduleList = new ArrayList<>();
        List<MissingScheduleByDate> scheduleListByDateList = new ArrayList<>();
        List<Integer> scheduleCountList = new ArrayList<>();
        LocalDate scheduleDate = missingScheduleList.get(0).getDate();

        for(Schedule schedule : missingScheduleList) {
            if (!scheduleDate.isEqual(schedule.getDate())) {
                scheduleListByDateList.add(MissingScheduleByDate.of(DateAndTimeConvert.localDateConvertString(scheduleDate),
                                DateAndTimeConvert.localDateConvertDayOfWeek(scheduleDate),
                                scheduleList,
                                scheduleCountList));
                scheduleDate = schedule.getDate();
                scheduleList.clear();
                scheduleCountList.clear();
            }
            scheduleCountList.add(getExpectedScheduleCount(schedule));
            scheduleList.add(schedule);
        }
        scheduleListByDateList.add(MissingScheduleByDate.of(DateAndTimeConvert.localDateConvertString(scheduleDate), DateAndTimeConvert.localDateConvertDayOfWeek(scheduleDate), scheduleList, scheduleCountList));
        return GetMissingAttendanceScheduleResponseDto.ofSchedule(scheduleListByDateList);
    }

    @Transactional
    public void updateSchedule(Long userIdx, UpdateScheduleRequestDto request) {
        // 유저 존재 여부
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 스케줄 존재 여부
        Schedule schedule = scheduleRepository.findById(request.getSchedule().getIdx())
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_SCHEDULE_EXCEPTION, ErrorStatus.NOT_FOUND_SCHEDULE_EXCEPTION.getMessage()));
        // 출결 상태가 존재하는 스케줄 체크
        if(!schedule.getStatus().equals(ScheduleStatus.NO_STATUS))
            throw new AlreadyUpdateScheduleAttendanceException(ErrorStatus.ALREADY_UPDATE_SCHEDULE_ATTENDANCE_EXCEPTION, ErrorStatus.ALREADY_UPDATE_SCHEDULE_ATTENDANCE_EXCEPTION.getMessage());
        // 출결 완료 상태 스케줄 보다 이전 날짜로 변경 불가
        Schedule recentUpdateStatusSchedule = scheduleRepository.findTopByLessonAndCycleAndStatusNotOrderByDateDesc(schedule.getLesson(), schedule.getCycle(), ScheduleStatus.NO_STATUS);
        if(recentUpdateStatusSchedule!=null && recentUpdateStatusSchedule.getDate().isAfter(DateAndTimeConvert.stringConvertLocalDate(request.getSchedule().getDate()))){
            System.out.println(recentUpdateStatusSchedule.getDate());
            throw new InvalidScheduleDateException(ErrorStatus.INVALID_SCHEDULE_DATE_EXCEPTION, ErrorStatus.INVALID_SCHEDULE_DATE_EXCEPTION.getMessage());
        }
        schedule.updateSchedule(
                DateAndTimeConvert.stringConvertLocalDate(request.getSchedule().getDate()),
                DateAndTimeConvert.stringConvertLocalTime(request.getSchedule().getStartTime()),
                DateAndTimeConvert.stringConvertLocalTime(request.getSchedule().getEndTime()));
    }

    @Transactional
    public UpdateScheduleAttendanceResponseDto updateScheduleAttendance(Long userIdx, UpdateScheduleAttendanceRequestDto request) {
        // 유저 존재 여부
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 유저가 선생님인지 확인
        if(!user.isMatchedRole(Role.TEACHER)) throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        // 스케줄 존재 여부
        Schedule schedule = scheduleRepository.findById(request.getSchedule().getIdx())
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_SCHEDULE_EXCEPTION, ErrorStatus.NOT_FOUND_SCHEDULE_EXCEPTION.getMessage()));
        // 출결 가능 이전 스케줄은 수정 불가
        if(schedule.getDate().isAfter(LocalDate.now()) || (schedule.getDate().isEqual(LocalDate.now()) && schedule.getStartTime().isAfter(LocalTime.now()))) throw new InvalidAttendanceDateException(ErrorStatus.INVALID_ATTENDANCE_DATE_EXCEPTION, ErrorStatus.INVALID_ATTENDANCE_DATE_EXCEPTION.getMessage());
        // 취소 상태에서는 수정 불가
        if(schedule.getStatus().equals(ScheduleStatus.CANCEL)) throw new InvalidAttendanceStatusException(ErrorStatus.INVALID_ATTENDANCE_STATUS_EXCEPTION, ErrorStatus.INVALID_ATTENDANCE_STATUS_EXCEPTION.getMessage());
        // 이전 스케줄 누락되면 불가
        boolean isAfterMissingAttendance = scheduleRepository.existsByLessonAndCycleAndStatusAndDateIsBefore(schedule.getLesson(), schedule.getCycle(), ScheduleStatus.NO_STATUS, schedule.getDate());
        boolean isTodayMissingAttendance = scheduleRepository.existsByLessonAndCycleAndStatusAndDateAndStartTimeLessThanEqualAndIdxNot(schedule.getLesson(), schedule.getCycle(), ScheduleStatus.NO_STATUS, schedule.getDate(), schedule.getStartTime(), schedule.getIdx());
        if(isAfterMissingAttendance || isTodayMissingAttendance) throw new InvalidAttendanceScheduleException(ErrorStatus.INVALID_ATTENDANCE_SCHEDULE_EXCEPTION, ErrorStatus.INVALID_ATTENDANCE_SCHEDULE_EXCEPTION.getMessage());

        // 스케줄 업데이트
        schedule.updateScheduleAttendance(request.getSchedule().getStatus());
        // 취소로 변경되면 스케줄 자동 생성
        Schedule lastSchedule = scheduleRepository.findTopByLessonAndCycleOrderByDateDesc(schedule.getLesson(), schedule.getCycle());
        if(schedule.getStatus().equals(ScheduleStatus.CANCEL)) scheduleRepository.saveAll(Schedule.autoCreateSchedule(lastSchedule.getDate().plusDays(1), 1L, schedule.getLesson()));

        // 진짜 마지막 회차인지 여부 (마지막 스케줄인지)
        boolean isLastCount = !scheduleRepository.existsByLessonAndCycleAndStatus(schedule.getLesson(), schedule.getCycle(), ScheduleStatus.NO_STATUS);
        return UpdateScheduleAttendanceResponseDto.of(isLastCount, LocalDate.now());
    }

    public GetLatestScheduleByTeacherResponseDto getLatestScheduleByTeacher(Long userIdx) {
        // 유저 존재 여부
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 선생님 여부
        if(!user.isMatchedRole(Role.TEACHER)) throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        // 가장 최근 스케줄 가져오기 (오늘 포함)
        List<Lesson> lessonList = lessonRepository.findAllByTeacherIdxAndIsFinished(userIdx, false);
        List<Schedule> scheduleList = scheduleRepository.findAllByLessonInAndDateGreaterThanEqualOrderByDate(lessonList, LocalDate.now());
        // 오늘인지 체크
        if(scheduleList.isEmpty()) return null;
        List<Schedule> latestScheduleList = new ArrayList<>();
        // 날짜 달라지면 그만
        LocalDate standardDate = scheduleList.get(0).getDate();
        for(Schedule schedule : scheduleList) {
            if(schedule.getDate().equals(standardDate)) latestScheduleList.add(schedule);
            else break;
        }
        latestScheduleList.sort(Comparator.comparing(Schedule::getStartTime));
        return GetLatestScheduleByTeacherResponseDto.of(standardDate, latestScheduleList);
    }

    @Transactional
    public void postImmediateMissingAttendance() throws IOException {
        // 스케줄 중 date 오늘이고 endTime인 것
        List<Schedule> immediateScheduleList = scheduleRepository.findAllByDateAndEndTimeAndStatus(LocalDate.now(), LocalTime.now(), ScheduleStatus.NO_STATUS);
        if(immediateScheduleList.isEmpty()) return;
        for(Schedule schedule: immediateScheduleList) {
            User user = schedule.getLesson().getTeacher();
            if(user.getDeviceToken()!=null){
                String title = NotificationConstant.getAttendanceImmediateCheckTitle();
                String body = NotificationConstant.getAttendanceImmediateCheckContent();
                fcmService.sendMessage(user.getDeviceToken(), title, body);
                notificationLogRepository.save(NotificationLog.toEntity(schedule.getLesson().getParents(), title, body));
            }
        }
    }

    @Transactional
    public void postMissingAttendance() throws IOException {
        // 누락 출결 수업 종료 후 30분까지
        LocalTime now = LocalTime.now();
        LocalTime targetTime = LocalTime.of(now.getHour(),now.getMinute(),0,0).minusMinutes(30);
        List<Schedule> missingScheduleList = scheduleRepository.findAllByDateAndEndTimeAndStatus(LocalDate.now(), targetTime, ScheduleStatus.NO_STATUS);
        if(missingScheduleList.isEmpty()) return;
        for(Schedule schedule: missingScheduleList) {
            User user = schedule.getLesson().getTeacher();
            if(user.getDeviceToken()!=null){
                String title = NotificationConstant.getAttendanceLateCheckTitle();
                String body = NotificationConstant.getAttendanceLateCheckContent();
                fcmService.sendMessage(user.getDeviceToken(), title, body);
                notificationLogRepository.save(NotificationLog.toEntity(schedule.getLesson().getParents(), title, body));
            }
        }
    }

    public GetTemporaryScheduleResponseDto getTemporarySchedule(GetTemporaryScheduleRequestDto request) {
        List<RegularSchedule> regularScheduleList = request.getRegularScheduleList().stream().map(r -> RegularSchedule.toTemporaryEntity(r.getDayOfWeek(), r.getStartTime(), r.getEndTime())).collect(Collectors.toList());

        List<Schedule> scheduleList = Schedule.autoCreateTemporarySchedule(DateAndTimeConvert.stringConvertLocalDate(request.getStartDate()), request.getCount(), regularScheduleList);
        List<TemporarySchedule> temporaryScheduleList = new ArrayList<>();
        LocalDate scheduleDate = scheduleList.get(0).getDate();
        List<Schedule> temporaryScheduleByTime = new ArrayList<>();

        for (Schedule schedule : scheduleList) {
            if (!scheduleDate.isEqual(schedule.getDate())) {
                temporaryScheduleList.add(TemporarySchedule.of(request.getStudentName(), request.getSubject(), DateAndTimeConvert.localDateConvertString(scheduleDate), temporaryScheduleByTime));
                scheduleDate = schedule.getDate();
                temporaryScheduleByTime.clear();
            }
            temporaryScheduleByTime.add(schedule);
        }
        temporaryScheduleList.add(TemporarySchedule.of(request.getStudentName(), request.getSubject(), DateAndTimeConvert.localDateConvertString(scheduleDate), temporaryScheduleByTime));
        return GetTemporaryScheduleResponseDto.of(temporaryScheduleList);
    }

    public Boolean getMissingAttendanceExistenceByLesson(Long userIdx, Long lessonIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        // 유저가 선생님인지 확인
        if(!user.isMatchedRole(Role.TEACHER)) throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());

        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findByIdxAndIsFinished(lessonIdx, false)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));

        // 출석누락 유무
        boolean isMissingAttendance = false;
        boolean isAfterMissingAttendance = scheduleRepository.existsByStatusAndDateIsBeforeAndLesson(ScheduleStatus.NO_STATUS, LocalDate.now(), lesson);
        boolean isTodayMissingAttendance = scheduleRepository.existsByStatusAndDateAndStartTimeLessThanEqualAndLessonOrderByDate(ScheduleStatus.NO_STATUS, LocalDate.now(), LocalTime.now(), lesson);
        if(isAfterMissingAttendance || isTodayMissingAttendance) isMissingAttendance = true;
        return isMissingAttendance;
    }

    public Boolean getMissingAttendanceExistenceByTeacher(Long userIdx) {
        // 유저 존재 여부
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 선생님 여부
        if(!user.isMatchedRole(Role.TEACHER)) throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        // 수업 리스트 가져오기
        List<Lesson> lessonList = lessonRepository.findAllByTeacherIdxAndIsFinished(userIdx, false);
        // 출석누락 유무
        boolean isMissingAttendance = false;
        boolean isAfterMissingAttendance = scheduleRepository.existsByStatusAndDateIsBeforeAndLessonIn(ScheduleStatus.NO_STATUS, LocalDate.now(), lessonList);
        boolean isTodayMissingAttendance = scheduleRepository.existsByStatusAndDateAndStartTimeLessThanEqualAndLessonInOrderByDate(ScheduleStatus.NO_STATUS, LocalDate.now(), LocalTime.now(), lessonList);
        if(isAfterMissingAttendance || isTodayMissingAttendance) isMissingAttendance = true;
        return isMissingAttendance;
    }



    public Boolean getTodayScheduleExistenceByTeacher(Long userIdx) {
        // 유저 존재 여부
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 선생님 여부
        if(!user.isMatchedRole(Role.TEACHER)) throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        // 수업 리스트 가져오기
        List<Lesson> lessonList = lessonRepository.findAllByTeacherIdxAndIsFinished(userIdx, false);
        // TODO 성능 고민 (queryDSL, exists)
        List<Schedule> scheduleList = scheduleRepository.findAllByLessonInAndDate(lessonList, LocalDate.now());
        return !scheduleList.isEmpty();
    }

    public List<GetLessonScheduleResponseDto> getLessonSchedule(Long userIdx, Long lessonIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findById(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));
        // 수업과 유저 연결 여부 확인
        if (!lesson.isMatchedUser(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());

        //오늘날짜, 현재시간 구하기
        LocalDate today = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        // 레슨 스케쥴 정보 구성
        List<GetLessonScheduleResponseDto> getLessonScheduleResponseDtoList = new ArrayList<>();
        scheduleRepository.findAllByLessonAndCycleOrderByDateDesc(lesson,lesson.getCycle())
                .forEach(s->{
                    //스케쥴날짜가 오늘날짜보다 이전인지 확인
                    if(today.isAfter(s.getDate())){
                        getLessonScheduleResponseDtoList.add(
                                GetLessonScheduleResponseDto.of(
                                        s.getIdx(),
                                        DateAndTimeConvert.localDateConvertString(s.getDate()),
                                        s.getStatus().getValue(),
                                        DateAndTimeConvert.localTimeConvertString(s.getStartTime()),
                                        DateAndTimeConvert.localTimeConvertString(s.getEndTime())));
                    }
                    if(today.isEqual(s.getDate())){
                        //수업시작시간확인

                        if(!nowTime.isBefore(s.getStartTime())){
                            getLessonScheduleResponseDtoList.add(
                                    GetLessonScheduleResponseDto.of(
                                            s.getIdx(),
                                            DateAndTimeConvert.localDateConvertString(s.getDate()),
                                            s.getStatus().getValue(),
                                            DateAndTimeConvert.localTimeConvertString(s.getStartTime()),
                                            DateAndTimeConvert.localTimeConvertString(s.getEndTime())));
                        }

                    }
                });
        return getLessonScheduleResponseDtoList;



    }

    public Boolean getAttendanceExistenceByTeacher(Long userIdx, Long scheduleIdx) {
        // 유저 존재 여부
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 선생님 여부
        if(!user.isMatchedRole(Role.TEACHER)) throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        // 스케줄 존재 여부
        Schedule schedule = scheduleRepository.findById(scheduleIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_SCHEDULE_EXCEPTION, ErrorStatus.NOT_FOUND_SCHEDULE_EXCEPTION.getMessage()));
        // 출결 상태가 존재하는 스케줄 체크
        return !schedule.getStatus().equals(ScheduleStatus.NO_STATUS);
    }
}
