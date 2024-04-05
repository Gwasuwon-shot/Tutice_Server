package gwasuwonshot.tutice.lesson.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.common.module.ReturnLongMath;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonRequest;
import gwasuwonshot.tutice.lesson.dto.response.LessonResponse;
import gwasuwonshot.tutice.lesson.dto.response.createLesson.CreateLessonResponse;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher.LessonByTeacher;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher.LatestRegularSchedule;
import gwasuwonshot.tutice.lesson.dto.response.getLessonDetail.GetLessonDetailResponse;
import gwasuwonshot.tutice.lesson.dto.response.getLessonExistenceByUser.GetLessonExistenceByUserResponse;
import gwasuwonshot.tutice.lesson.dto.response.getLessonRegularSchedule.LessonRegularSchedule;
import gwasuwonshot.tutice.lesson.dto.response.getLessonRegularSchedule.GetLessonRegularScheduleResponse;
import gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenanceLessonByTeacher.MissingMaintenanceLesson;
import gwasuwonshot.tutice.lesson.entity.*;
import gwasuwonshot.tutice.lesson.exception.conflict.AlreadyExistLessonParentsException;
import gwasuwonshot.tutice.lesson.exception.conflict.AlreadyFinishedLessonException;
import gwasuwonshot.tutice.lesson.exception.invalid.InvalidLessonCodeException;
import gwasuwonshot.tutice.lesson.exception.invalid.InvalidLessonException;
import gwasuwonshot.tutice.lesson.exception.notfound.NotFoundLessonException;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.lesson.repository.PaymentRecordRepository;
import gwasuwonshot.tutice.lesson.repository.RegularScheduleRepository;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.entity.ScheduleStatus;
import gwasuwonshot.tutice.schedule.repository.ScheduleRepository;
import gwasuwonshot.tutice.user.entity.Account;
import gwasuwonshot.tutice.user.entity.Bank;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.exception.userException.InvalidRoleException;
import gwasuwonshot.tutice.user.exception.userException.NotFoundUserException;
import gwasuwonshot.tutice.user.repository.AccountRepository;
import gwasuwonshot.tutice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final RegularScheduleRepository regularScheduleRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final ScheduleRepository scheduleRepository;


    @Transactional
    public CreateLessonResponse createLesson(
            final Long userIdx, final CreateLessonRequest request) {

        User teacher = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        //0. 역할이 선생님이 아니면 에러발생 // TODO : 서비스단에는 도메인로직이 들어있으면 안됨.
        if (!teacher.isMatchedRole(Role.TEACHER)) {
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION, ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }


        //1. 선생님 계좌등록
        Payment payment = Payment.getByValue(request.getLesson().getPayment());

        Account account = Account.toEntity(
                teacher,
                request.getAccount().getName(),
                Bank.getByValue(request.getAccount().getBank()),
                request.getAccount().getNumber()
        );
        teacher.addAccount(account);
        accountRepository.save(account);


        //2. 레슨등록
        Lesson lesson = Lesson.toEntity(
                teacher,
                account,
                request.getLesson().getSubject(),
                request.getLesson().getStudentName(),
                request.getLesson().getCount(),
                payment,
                request.getLesson().getAmount(),
                DateAndTimeConvert.stringConvertLocalDate(request.getLesson().getStartDate())

        );

        teacher.addLesson(lesson);
        lessonRepository.save(lesson);


        //2.1 레슨이 선불일 경우 가짜 PaymentRecord 생성
        Long prePaymentRecordIdx = -1L;
        if (lesson.isMatchedPayment(Payment.PRE_PAYMENT)) {
            PaymentRecord prePaymentRecord = PaymentRecord.toEntity(lesson);
            paymentRecordRepository.save(prePaymentRecord);
            lesson.addPaymentRecord(prePaymentRecord);
            prePaymentRecordIdx = prePaymentRecord.getIdx();
        }


        //3. 해당 레슨 정기일정 생성
        //?근본적인의문 : builder와 어셈블러가 다른이유를 모르겠음?/

        List<RegularSchedule> regularScheduleList = request.getLesson().getRegularScheduleList().stream()
                .map(rs -> regularScheduleRepository.save(
                        RegularSchedule.toEntity(
                                lesson,
                                DateAndTimeConvert.stringConvertLocalTime(rs.getStartTime()),
                                DateAndTimeConvert.stringConvertLocalTime(rs.getEndTime()),
                                DayOfWeek.getByValue(rs.getDayOfWeek())
                        )
                )).collect(Collectors.toList());

        regularScheduleList.forEach(a -> {
            lesson.addRegularSchedule(a);
        });


        //4. 스케쥴 자동생성 (무조건 스케쥴 자동생성전에 가짜 paymentRecord 추가가 선행되어야함)
        scheduleRepository.saveAll(Schedule.autoCreateSchedule(lesson.getStartDate(), lesson.getCount(), lesson));


        //레슨코드 생성
        String createdLessonCode = lesson.createLessonCode();

        if (lesson.isMatchedPayment(Payment.PRE_PAYMENT)) {
            //선불인경우만 payment와 lessonIdx 주기
            //제대로 오나?
            return CreateLessonResponse.of(createdLessonCode, prePaymentRecordIdx, lesson.getIdx());

        }
        return CreateLessonResponse.of(createdLessonCode, null, null);

    }


    @Transactional
    public Boolean createLessonMaintenance(Long userIdx, Long lessonIdx, Boolean isLessonMaintenance) {
//        //연장시
//        //연장안할때
//        유저가 선생이 맞는지 확인
//        유효한 레슨인지, 선생의 레슨이 맞는지확인
//        연장안하면 -> isFinished 만 true로 변경
//                연장하면
//        가짜 paymentRecord생성
//        startDate는 해당 수업의 마지막 스케쥴날짜 +1

        User teacher = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        //0. 역할이 선생님이 아니면 에러발생 // TODO : 서비스단에는 도메인로직이 들어있으면 안됨.
        if (!teacher.isMatchedRole(Role.TEACHER)) {
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION, ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }

        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findByIdxAndIsFinishedAndDeletedAtIsNull(lessonIdx, false)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));


        if (!lesson.isMatchedTeacher(teacher)) {
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_EXCEPTION.getMessage());
        }


        if (isLessonMaintenance) {
            //연장하면
            ////        가짜 paymentRecord생성
            paymentRecordRepository.save(PaymentRecord.toEntity(lesson));

            ////        startDate는 해당 수업의 마지막 스케쥴날짜 +1
            LocalDate maintenanceDate = scheduleRepository.findTopByLessonOrderByDateDesc(lesson).getDate().plusDays(1);

            //연장...!
            Schedule.autoCreateSchedule(maintenanceDate, lesson.getCount(), lesson)
                    .forEach(acs -> scheduleRepository.save(acs));
            ;


            return true;

        } else {//연장안하면, isFinished 만 true로 변경
            if (lesson.getIsFinished()) {
                throw new AlreadyFinishedLessonException(ErrorStatus.ALREADY_FINISHED_LESSON_EXCEPTION, ErrorStatus.ALREADY_FINISHED_LESSON_EXCEPTION.getMessage());
            }
            lesson.finishLesson();
            return false;
        }
    }


    @Transactional
    public GetLessonExistenceByUserResponse getLessonExistenceByUser(final Long userIdx) {
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if (user.isMatchedRole(Role.PARENTS)) {
            return GetLessonExistenceByUserResponse.of(!(lessonRepository.findAllByParentsIdxAndIsFinishedAndDeletedAtIsNull(user.getIdx(), false).isEmpty())
                    , user.getName());

        } else if (user.isMatchedRole(Role.TEACHER)) {
            return GetLessonExistenceByUserResponse.of(!(lessonRepository.findAllByTeacherIdxAndIsFinishedAndDeletedAtIsNull(user.getIdx(), false).isEmpty())
                    , user.getName());
        } else {
            return null; // 관리자 계정일경우..... 뭐하지??
        }
    }


    @Transactional
    public List<LessonByTeacher> getLessonByTeacher(final Long userIdx) {
        List<LessonByTeacher> lessonByTeacherList = new ArrayList<>();

//        유저가 존재하고 선생님이 맞는지 확인
        User teacher = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        //0. 역할이 선생님이 아니면 에러발생 // TODO : 서비스단에는 도메인로직이 들어있으면 안됨.
        if (!teacher.isMatchedRole(Role.TEACHER)) {
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION, ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }


        // 유저에 연결된 레슨리스트 다가져오기
        teacher.getLessonList().forEach(l -> {

            // 먼저 레슨이 삭제되었는지 확인
            if (l.getDeletedAt() != null) {
                return;
            }

            // 각수업당 다가오는 가장 빠른 요일가져오기
            RegularSchedule latestRegularSchedule = RegularSchedule.findLatestRegularSchedule(LocalDate.now(), l.getRegularScheduleList());

            //  각레슨의 진짜회차를 계산해 percent 계산
            // TODO : nowCount - percent 로직 겹침. 모듈로 빼기
//        - [ ] nowCount : 진짜 카운트 : 현재 사이클의 스케쥴중 출결정보가 있는스케쥴개수
            Long nowCount = scheduleRepository.countByLessonAndCycleAndStatusIn(l, l.getCycle(), ScheduleStatus.getAttendanceScheduleStatusList());
            //                - [ ] percent : 전체카운트와 진짜카운트의 백분율
            Long percent = ReturnLongMath.getPercentage(nowCount, l.getCount());

            lessonByTeacherList.add(LessonByTeacher.of(l.getIdx(), l.getStudentName(), l.getSubject(), percent, l.getIsFinished(),
                    LatestRegularSchedule.of(latestRegularSchedule.getDayOfWeek().getValue(), latestRegularSchedule.getStartTime(), latestRegularSchedule.getEndTime())));


        });

        return lessonByTeacherList;
    }


    @Transactional
    public List<LessonResponse> getLessonByParents(final Long userIdx) {
//        유저의 역할이 학부모인지 받기

        List<LessonResponse> lessonResponseList = new ArrayList<>();

        User parents = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        //0. 역할이 부모님이 아니면 에러발생 // TODO : 서비스단에는 도메인로직이 들어있으면 안됨.
        if (!parents.isMatchedRole(Role.PARENTS)) {
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION, ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }

//        유저에 연결된 수업리스트가져오기
        lessonRepository.findAllByParentsIdxAndIsFinishedAndDeletedAtIsNull(parents.getIdx(), false)
                .forEach(pl -> {

                            //  현재 회차계산 : 이때 수업에 연결된 스케쥴중 현재사이클(수업에 연결된 paymentRecord개수(선불,후불+1))중 출석,결석만 카운트해서 현재카운트가져오기


                            // TODO : nowCount - percent 로직 겹침. 모듈로 빼기
                            Long nowCount = scheduleRepository.countByLessonAndCycleAndStatusIn(pl, pl.getCycle(), ScheduleStatus.getAttendanceScheduleStatusList());
                            Long percent = ReturnLongMath.getPercentage(nowCount, pl.getCount());
                            lessonResponseList.add(
                                    LessonResponse.of(
                                            pl.getIdx(), pl.getTeacher().getName(), pl.getStudentName(), pl.getSubject(), pl.getCount(), nowCount, percent));
                        }
                );


        return lessonResponseList;


    }


    @Transactional
    public List<MissingMaintenanceLesson> getMissingMaintenanceLessonByTeacher(Long userIdx) {

        //1.  유저가져와서 역할검사
        User teacher = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if (!teacher.isMatchedRole(Role.TEACHER)) {
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION, ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }


        List<MissingMaintenanceLesson> missingMaintenanceLessonList = new ArrayList<>();

        // TODO : 아래의 로직좀더 효율적으로 리팩하기
        //2. 유저에 연결된 레슨가져오기
        //2.1 레슨이 isFinished가 false이고00
        lessonRepository.findAllByTeacherIdxAndIsFinishedAndDeletedAtIsNull(teacher.getIdx(), false)
                .forEach(lfn -> {
                    // 2.2 간단플래그 : 현재사이클의 가장 최근 스케쥴의 상태가 상태없음이 아닐경우 (사실 최근스케쥴은 출석 or 결석만 되긴함)
                    Schedule endSchedule = scheduleRepository.findTopByLessonAndCycleAndStatusNotOrderByDateDesc(lfn, lfn.getCycle(), ScheduleStatus.CANCEL);
                    if (!endSchedule.isMatchedStatus(ScheduleStatus.NO_STATUS)) {
                        missingMaintenanceLessonList.add(MissingMaintenanceLesson.of(
                                LessonResponse.of(
                                        lfn.getIdx(), lfn.getStudentName(), lfn.getSubject(), lfn.getCount())
                                , DateAndTimeConvert.localDateConvertString(endSchedule.getDate()))

                        );
                    }

                });

        return missingMaintenanceLessonList;


    }


    public Long getLessonIdxFromLessonCode(String lessonCode) {
        try {
            byte[] lessonIdxBytes = Base64.getDecoder().decode(lessonCode);
            Long lessonIdx = Long.parseLong(new String(lessonIdxBytes));
            return lessonIdx;
        } catch (Exception e) {
            throw new InvalidLessonCodeException(ErrorStatus.INVALID_LESSON_CODE_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());
        }
    }


    public LessonResponse getLessonProgress(Long userIdx, Long lessonIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findByIdxAndDeletedAtIsNull(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));

        // 수업과 유저 연결 여부 확인
        if (!lesson.isMatchedUser(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());

        // 현재 회차 계산 : 이때 수업에 연결된 스케쥴중 현재사이클(수업에 연결된 paymentRecord개수(선불,후불+1))중 출석,결석만 카운트해서 현재카운트가져오기
        Long nowCount = scheduleRepository.countByLessonAndCycleAndStatusIn(lesson, lesson.getCycle(), ScheduleStatus.getAttendanceScheduleStatusList());
        // - [ ] percent : 전체카운트와 진짜카운트의 백분율
        Long percent = ReturnLongMath.getPercentage(nowCount, lesson.getCount());

        return LessonResponse.of(lesson.getIdx(), lesson.getCount(), nowCount, percent);
    }

    public GetLessonRegularScheduleResponse getLessonRegularSchedule(Long userIdx, Long lessonIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findByIdxAndDeletedAtIsNull(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));


        // 수업과 유저 연결 여부 확인
        if (!lesson.isMatchedUser(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());


        List<LessonRegularSchedule> lessonRegularScheduleList = new ArrayList<>();

        // 정기일정중 시간이 중복되는 경우 검사
        RegularSchedule.groupByTimeRegularScheduleIndexList(lesson.getRegularScheduleList())
                .forEach(gl -> {
                    List<DayOfWeek> dayOfWeeksList = new ArrayList<>();
                    LocalTime startTime = lesson.getRegularScheduleList().get(gl.get(0)).getStartTime();
                    LocalTime endTime = lesson.getRegularScheduleList().get(gl.get(0)).getEndTime();

                    gl.forEach(d ->
                            dayOfWeeksList.add(lesson.getRegularScheduleList().get(d).getDayOfWeek()));

                    // dayOfWeekList 정렬
                    DayOfWeek.sorted(dayOfWeeksList);

                    lessonRegularScheduleList.add(LessonRegularSchedule.of(dayOfWeeksList, startTime, endTime));
                });

        // 첫번째 DayOfWeek 순서로 정렬필요
        if (lessonRegularScheduleList.size() > 1) {
            Collections.sort(lessonRegularScheduleList, new Comparator<LessonRegularSchedule>() {
                @Override
                public int compare(LessonRegularSchedule o1, LessonRegularSchedule o2) {
                    return DayOfWeek.getIndexByValue(o1.getDayOfWeekList().get(0)) - DayOfWeek.getIndexByValue(o2.getDayOfWeekList().get(0));
                }
            });
        }


        return GetLessonRegularScheduleResponse.of(lessonRegularScheduleList);


    }

    public GetLessonDetailResponse getLessonDetail(Long userIdx, Long lessonIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findByIdxAndDeletedAtIsNull(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));


        // 수업과 유저 연결 여부 확인
        if (!lesson.isMatchedUser(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());

        return GetLessonDetailResponse.of(lesson);
    }


    public Boolean getMissingMaintenanceExistenceByTeacher(Long userIdx) {
        // 유저 존재 여부
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 선생님 여부
        if (!user.isMatchedRole(Role.TEACHER))
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION, ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        // 수업 리스트 가져오기
        List<Lesson> lessonList = lessonRepository.findAllByTeacherIdxAndIsFinishedAndDeletedAtIsNull(userIdx, false);
        // 수업연장 여부 유무
        boolean isMissingMaintenance = false;
        for (Lesson lesson : lessonList) {
            if (isMissingMaintenance) break;
            isMissingMaintenance = !scheduleRepository.existsByLessonAndCycleAndStatus(lesson, lesson.getCycle(), ScheduleStatus.NO_STATUS);
        }
        return isMissingMaintenance;
    }

    @Transactional
    public void updateLessonParents(Long userIdx, String lessonCode) {
        //1. 유저가 학부모가 맞는지 보기
        User parents = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        //0. 역할이 선생님이 아니면 에러발생
        if (!parents.isMatchedRole(Role.PARENTS)) {
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION, ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }
        //2. 레슨코드해석
        Long lessonIdx = this.getLessonIdxFromLessonCode(lessonCode);
        //3. 해석한 레슨아이디로 레슨찾기 -> 없으면 404
        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findByIdxAndIsFinishedAndDeletedAtIsNull(lessonIdx, false)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));

        //4. 레슨에 연결된 학부모검사 -> 현유저가 아닌유저이면 409
        if (lesson.getParents() == null) {
            lesson.connectParents(parents);

        } else if (lesson.getParents().equals(parents)) {
            //이미 같은유저가 연결된거면 성공으로 치기
        } else {
            throw new AlreadyExistLessonParentsException(ErrorStatus.ALREADY_EXIST_LESSON_PARENTS_EXCEPTION, ErrorStatus.ALREADY_EXIST_LESSON_PARENTS_EXCEPTION.getMessage());
        }

    }

    @Transactional
    public void deleteLesson(Long userIdx, Long lessonIdx) {
        //유저 존재여부
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 선생님 여부
        if (!user.isMatchedRole(Role.TEACHER))
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION, ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());


        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findByIdxAndDeletedAtIsNull(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));


        // 수업과 유저 연결 여부 확인
        if (!lesson.isMatchedUser(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());

        // 수업 삭제 처리
        lesson.deleteLesson();
    }
}