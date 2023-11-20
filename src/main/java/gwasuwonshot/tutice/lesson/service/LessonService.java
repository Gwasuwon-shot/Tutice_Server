package gwasuwonshot.tutice.lesson.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.common.module.ReturnLongMath;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonRequestDto;
import gwasuwonshot.tutice.lesson.dto.response.CreateLessonResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonByUserResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonDetailResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonProgressResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByParents.GetLessonByParents;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher.GetLessonByTeacher;
import gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenance.GetMissingMaintenanceLesson;
import gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenance.MissingMaintenanceLesson;
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
import gwasuwonshot.tutice.user.dto.assembler.AccountAssembler;
import gwasuwonshot.tutice.user.entity.Account;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final AccountAssembler accountAssembler;
    private final AccountRepository accountRepository;
    private final RegularScheduleRepository regularScheduleRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final ScheduleRepository scheduleRepository;


    @Transactional
    public GetLessonByUserResponseDto getLessonExistenceByUser(final Long userIdx){
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if(user.isMatchedRole(Role.PARENTS)){
            return GetLessonByUserResponseDto.of(!(lessonRepository.findAllByParentsIdxAndIsFinished(user.getIdx(), false).isEmpty())
                    ,user.getName());

        } else if (user.isMatchedRole(Role.TEACHER)) {
            return GetLessonByUserResponseDto.of(!(lessonRepository.findAllByTeacherIdxAndIsFinished(user.getIdx(),false).isEmpty())
                    ,user.getName());
        }
        else{
            return null; // 관리자 계정일경우..... 뭐하지??
        }
    }


    @Transactional
    public List<GetLessonByTeacher> getLessonByTeacher(final Long userIdx){
        List<GetLessonByTeacher> getLessonByTeacherList= new ArrayList<>();

//        유저가 존재하고 선생님이 맞는지 확인
        User teacher = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        //0. 역할이 선생님이 아니면 에러발생 // TODO : 서비스단에는 도메인로직이 들어있으면 안됨.
        if(!teacher.isMatchedRole(Role.TEACHER)){
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }

//        유저에 연결된 레슨리스트 다가져오기
        teacher.getLessonList().forEach(l->{
//        각레슨의 정기스케쥴 가져와 일주일순서로 정렬
            List<String> dayOfWeekList = new ArrayList<>();
            RegularSchedule.dayOfWeekSortedReglarScheduleList(l.getRegularScheduleList());
            l.getRegularScheduleList()
                    .forEach(rs->dayOfWeekList.add(rs.getDayOfWeek().getValue()));
//        각레슨의 진짜회차를 계산해 percent 계산
            // TODO : nowCount - percent 로직 겹침. 모듈로 빼기
//        - [ ] nowCount : 진짜 카운트 : 현재 사이클의 스케쥴중 출결정보가 있는스케쥴개수
            Long nowCount = scheduleRepository.countByLessonAndCycleAndStatusIn(l,l.getCycle(),ScheduleStatus.getAttendanceScheduleStatusList());
            //                - [ ] percent : 전체카운트와 진짜카운트의 백분율
            Long percent = ReturnLongMath.getPercentage(nowCount,l.getCount());

            getLessonByTeacherList.add(GetLessonByTeacher.of(l.getIdx(),l.getStudentName(), l.getSubject(),percent,dayOfWeekList));


        });

        return getLessonByTeacherList;
    }




    @Transactional
    public List<GetLessonByParents> getLessonByParents(final Long userIdx){
//        유저의 역할이 학부모인지 받기

        List<GetLessonByParents> getLessonByParentsList = new ArrayList<>();

        User parents = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        //0. 역할이 부모님이 아니면 에러발생 // TODO : 서비스단에는 도메인로직이 들어있으면 안됨.
        if(!parents.isMatchedRole(Role.PARENTS)){
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }

//        유저에 연결된 수업리스트가져오기
        lessonRepository.findAllByParentsIdxAndIsFinished(parents.getIdx(),false)
                .forEach(pl->{

                    //  현재 회차계산 : 이때 수업에 연결된 스케쥴중 현재사이클(수업에 연결된 paymentRecord개수(선불,후불+1))중 출석,결석만 카운트해서 현재카운트가져오기


                    // TODO : nowCount - percent 로직 겹침. 모듈로 빼기
                    Long nowCount = scheduleRepository.countByLessonAndCycleAndStatusIn(pl,pl.getCycle(),ScheduleStatus.getAttendanceScheduleStatusList());
                    Long percent = ReturnLongMath.getPercentage(nowCount,pl.getCount());
                    getLessonByParentsList.add(
                            GetLessonByParents.of(
                                    pl.getIdx(),pl.getTeacher().getName(),pl.getStudentName(),pl.getSubject(),pl.getCount(),nowCount,percent));
                }
        );


        return getLessonByParentsList;


    }

    @Transactional
    public CreateLessonResponseDto createLesson(
            final Long userIdx, final CreateLessonRequestDto request){

        User teacher = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        //0. 역할이 선생님이 아니면 에러발생 // TODO : 서비스단에는 도메인로직이 들어있으면 안됨.
        if(!teacher.isMatchedRole(Role.TEACHER)){
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }



        //1. 선생님 계좌등록
        Payment payment = Payment.getPaymentByValue(request.getLesson().getPayment());

        Account account = accountAssembler.toEntity(
                teacher,
                request.getAccount().getName(),
                request.getAccount().getBank(),
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
        if(lesson.isMatchedPayment(Payment.PRE_PAYMENT)){
            PaymentRecord prePaymentRecord = PaymentRecord.toEntity(lesson);
            paymentRecordRepository.save(prePaymentRecord);
            lesson.addPaymentRecord(prePaymentRecord);
            prePaymentRecordIdx=prePaymentRecord.getIdx();
        }



        //3. 해당 레슨 정기일정 생성
        //?근본적인의문 : builder와 어셈블러가 다른이유를 모르겠음?/

        List<RegularSchedule> regularScheduleList = request.getLesson().getRegularScheduleList().stream()
                .map(rs->regularScheduleRepository.save(
                        RegularSchedule.toEntity(
                                lesson,
                                DateAndTimeConvert.stringConvertLocalTime(rs.getStartTime()),
                                DateAndTimeConvert.stringConvertLocalTime(rs.getEndTime()),
                                DayOfWeek.getDayOfWeekByValue(rs.getDayOfWeek())
                        )
                )).collect(Collectors.toList());

        regularScheduleList.forEach(a->{
            lesson.addRegularSchedule(a);
        });


        //4. 스케쥴 자동생성 (무조건 스케쥴 자동생성전에 가짜 paymentRecord 추가가 선행되어야함)
       scheduleRepository.saveAll(Schedule.autoCreateSchedule(lesson.getStartDate(),lesson.getCount(),lesson));



        //레슨코드 생성
        String createdLessonCode = lesson.createLessonCode();

        if(lesson.isMatchedPayment(Payment.PRE_PAYMENT)){
            //선불인경우만 payment와 lessonIdx 주기
            //제대로 오나?
            return CreateLessonResponseDto.of(createdLessonCode, prePaymentRecordIdx, lesson.getIdx());

        }
        return CreateLessonResponseDto.of(createdLessonCode,null, null);

    }

    @Transactional
    public Boolean createLessonMaintenance(Long userIdx,Long lessonIdx, Boolean isLessonMaintenance){
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
        if(!teacher.isMatchedRole(Role.TEACHER)){
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }

        Lesson lesson = lessonRepository.findById(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION,ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));

        if(!lesson.isMatchedTeacher(teacher)){
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION,ErrorStatus.INVALID_LESSON_EXCEPTION.getMessage());
        }


        if(isLessonMaintenance){
            //연장하면
            ////        가짜 paymentRecord생성
            paymentRecordRepository.save(PaymentRecord.toEntity(lesson));

            ////        startDate는 해당 수업의 마지막 스케쥴날짜 +1
            LocalDate maintenanceDate = scheduleRepository.findTopByLessonOrderByDateDesc(lesson).getDate().plusDays(1);

            //연장...!
            Schedule.autoCreateSchedule(maintenanceDate,lesson.getCount(),lesson)
                    .forEach(acs->scheduleRepository.save(acs));;


            return true;

        }
        else{//연장안하면, isFinished 만 true로 변경
            if(lesson.getIsFinished()){
                throw new AlreadyFinishedLessonException(ErrorStatus.ALREADY_FINISHED_LESSON_EXCEPTION,ErrorStatus.ALREADY_FINISHED_LESSON_EXCEPTION.getMessage());
            }
            lesson.finishLesson();
            return false;
        }
    }

    @Transactional
    public List<GetMissingMaintenanceLesson> getMissingMaintenanceLessonByUser(Long userIdx){

        //1.  유저가져와서 역할검사
        User teacher = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if(!teacher.isMatchedRole(Role.TEACHER)){
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }


        List<GetMissingMaintenanceLesson> getMissingMaintenanceLessonList = new ArrayList<>();

        // TODO : 아래의 로직좀더 효율적으로 리팩하기
        //2. 유저에 연결된 레슨가져오기
        //2.1 레슨이 isFinished가 false이고00
        lessonRepository.findAllByTeacherIdxAndIsFinished(teacher.getIdx(),false)
                .forEach(lfn -> {
                    // 2.2 간단플래그 : 현재사이클의 가장 최근 스케쥴의 상태가 상태없음이 아닐경우 (사실 최근스케쥴은 출석 or 결석만 되긴함)
                    Schedule endSchedule = scheduleRepository.findTopByLessonAndCycleAndStatusNotOrderByDateDesc(lfn, lfn.getCycle(),ScheduleStatus.CANCEL);
                    if(!endSchedule.isMatchedStatus(ScheduleStatus.NO_STATUS)){
                        getMissingMaintenanceLessonList.add(GetMissingMaintenanceLesson.of(
                                MissingMaintenanceLesson.of(
                                        lfn.getIdx(), lfn.getStudentName(), lfn.getSubject(), lfn.getCount())
                                ,DateAndTimeConvert.localDateConvertString(endSchedule.getDate()))

                        );
                    }

                });

        return getMissingMaintenanceLessonList;


    }
    @Transactional
    public void updateLessonParents(Long userIdx, String lessonCode){
        //1. 유저가 학부모가 맞는지 보기
        User parents = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        //0. 역할이 선생님이 아니면 에러발생
        if(!parents.isMatchedRole(Role.PARENTS)){
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }
        //2. 레슨코드해석
        Long lessonIdx = this.getLessonIdxFromLessonCode(lessonCode);
        //3. 해석한 레슨아이디로 레슨찾기 -> 없으면 404
        Lesson lesson = lessonRepository.findById(lessonIdx)
                .orElseThrow(()->new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));
        //4. 레슨에 연결된 학부모검사 -> 현유저가 아닌유저이면 409
        if(lesson.getParents() == null){
            lesson.connectParents(parents);

        } else if (lesson.getParents().equals(parents)) {
            //이미 같은유저가 연결된거면 성공으로 치기
        }
        else{
            throw new AlreadyExistLessonParentsException(ErrorStatus.ALREADY_EXIST_LESSON_PARENTS_EXCEPTION, ErrorStatus.ALREADY_EXIST_LESSON_PARENTS_EXCEPTION.getMessage());
        }

    }

//    public String createLessonCode(Long lessonIdx){
//        //statless하게 lessonIdx의 정보를 가진 레슨코드를 생성하여 추후 레슨코드만 해석해도 어떤 레슨인지 알수있게
//        byte[] lessonIdxBytes = (lessonIdx+"").getBytes();
//        String lessonCode = Base64.getEncoder().encodeToString(lessonIdxBytes);
//
//        return lessonCode;
//    }

    public Long getLessonIdxFromLessonCode(String lessonCode){
        try{
            byte[] lessonIdxBytes = Base64.getDecoder().decode(lessonCode);
            Long lessonIdx = Long.parseLong(new String(lessonIdxBytes));
            return lessonIdx;
        }catch (Exception e){
            throw new InvalidLessonCodeException(ErrorStatus.INVALID_LESSON_CODE_EXCEPTION,ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());
        }
    }

//    public GetLessonAccountResponseDto getAccountByLesson(Long userIdx, Long lessonIdx) {
//        // 유저 존재 여부 확인
//        User user = userRepository.findById(userIdx)
//                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
//        // 수업 존재 여부 확인
//        Lesson lesson = lessonRepository.findById(lessonIdx)
//                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));
//        // 수업과 유저 연결 여부 확인
//        if (!lesson.isMatchedUser(user))
//            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());
//
//        return GetLessonAccountResponseDto.of(lesson.getAccount());
//    }

    public GetLessonProgressResponseDto getLessonProgress(Long userIdx, Long lessonIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findById(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));
        // 수업과 유저 연결 여부 확인
        if (!lesson.isMatchedUser(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());

        // 현재 회차 계산 : 이때 수업에 연결된 스케쥴중 현재사이클(수업에 연결된 paymentRecord개수(선불,후불+1))중 출석,결석만 카운트해서 현재카운트가져오기
        Long nowCount = scheduleRepository.countByLessonAndCycleAndStatusIn(lesson,lesson.getCycle(),ScheduleStatus.getAttendanceScheduleStatusList());
        // - [ ] percent : 전체카운트와 진짜카운트의 백분율
        Long percent = ReturnLongMath.getPercentage(nowCount, lesson.getCount());

        return GetLessonProgressResponseDto.of(lesson.getIdx(), lesson.getCount(), nowCount, percent);
    }

    public GetLessonDetailResponseDto getLessonDetail(Long userIdx, Long lessonIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findById(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));
        // 수업과 유저 연결 여부 확인
        if (!lesson.isMatchedUser(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());

        return GetLessonDetailResponseDto.of(lesson);
    }

//    public List<GetLessonScheduleResponseDto> getLessonSchedule(Long userIdx, Long lessonIdx) {
//        // 유저 존재 여부 확인
//        User user = userRepository.findById(userIdx)
//                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
//        // 수업 존재 여부 확인
//        Lesson lesson = lessonRepository.findById(lessonIdx)
//                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));
//        // 수업과 유저 연결 여부 확인
//        if (!lesson.isMatchedUser(user))
//            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());
//        // 레슨 스케쥴 정보 구성
//        List<GetLessonScheduleResponseDto> getLessonScheduleResponseDtoList = new ArrayList<>();
//        scheduleRepository.findAllByLessonAndCycleOrderByDateDesc(lesson,lesson.getCycle())
//                .forEach(s->{
//                    getLessonScheduleResponseDtoList.add(
//                            GetLessonScheduleResponseDto.of(
//                                    s.getIdx(),
//                                    DateAndTimeConvert.localDateConvertString(s.getDate()),
//                                    s.getStatus().getValue(),
//                                    DateAndTimeConvert.localTimeConvertString(s.getStartTime()),
//                                    DateAndTimeConvert.localTimeConvertString(s.getEndTime())));
//                });
//        return getLessonScheduleResponseDtoList;
//    }


    public Boolean getMissingMaintenanceExistenceByTeacher(Long userIdx) {
        // 유저 존재 여부
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 선생님 여부
        if(!user.isMatchedRole(Role.TEACHER)) throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        // 수업 리스트 가져오기
        List<Lesson> lessonList = lessonRepository.findAllByTeacherIdxAndIsFinished(userIdx, false);
        // 수업연장 여부 유무
        boolean isMissingMaintenance = false;
        for(Lesson lesson : lessonList) {
            if(isMissingMaintenance) break;
            isMissingMaintenance = !scheduleRepository.existsByLessonAndCycleAndStatus(lesson, lesson.getCycle(), ScheduleStatus.NO_STATUS);
        }
        return isMissingMaintenance;
    }

    @Transactional
    public void deleteLesson(Long userIdx, Long lessonIdx){
        //유저 존재여부
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 선생님 여부
        if(!user.isMatchedRole(Role.TEACHER)) throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());

        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findById(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));
        // 수업과 유저 연결 여부 확인
        if (!lesson.isMatchedUser(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());

        // 수업이 이미 종료되었는지 확인
        if(lesson.getIsFinished()){
            throw new AlreadyFinishedLessonException(ErrorStatus.ALREADY_FINISHED_LESSON_EXCEPTION,ErrorStatus.ALREADY_FINISHED_LESSON_EXCEPTION.getMessage());
        }
        // 수업 종료처리
        lesson.finishLesson();
    }
}
