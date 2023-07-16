package gwasuwonshot.tutice.lesson.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.dto.assembler.LessonAssembler;
import gwasuwonshot.tutice.lesson.dto.assembler.PaymentRecordAssembler;
import gwasuwonshot.tutice.lesson.dto.assembler.RegularScheduleAssembler;
import gwasuwonshot.tutice.lesson.dto.request.CreateLessonRequestDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonByUserResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonDetailByParentsResponseAccount;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonDetailByParentsResponseDto;
import gwasuwonshot.tutice.lesson.entity.DayOfWeek;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.Payment;
import gwasuwonshot.tutice.lesson.entity.RegularSchedule;
import gwasuwonshot.tutice.lesson.exception.NotFoundLessonException;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.lesson.repository.PaymentRecordRepository;
import gwasuwonshot.tutice.lesson.repository.RegularScheduleRepository;
import gwasuwonshot.tutice.schedule.entity.Schedule;
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


import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final LessonAssembler lessonAssembler;
    private final AccountAssembler accountAssembler;
    private final AccountRepository accountRepository;
    private final RegularScheduleAssembler regularScheduleAssembler;
    private final RegularScheduleRepository regularScheduleRepository;
    private final PaymentRecordAssembler paymentRecordAssembler;
    private final PaymentRecordRepository paymentRecordRepository;


    @Transactional
    public GetLessonDetailByParentsResponseDto getLessonDetailByParents(Long userIdx,Long lessonIdx){
        //1. 먼저 유저를 찾고 유저의 롤이 부모님확인
        User parents = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if(!parents.isMatchedRole(Role.PARENTS)){
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }



        // 2. 부모님의 수업중 해당 수업 아이디 있는지 확인
//        System.out.println("시작1"); // TODO : 이거 jpa 영속성 관련 이슈인것같은데... 구현하고 공부해보기....
//        parents.getLessonList().forEach(lesson -> System.out.println(lesson.getStudentName()));
//        System.out.println("끝1");




        Lesson lesson = lessonRepository.findAllByParentsIdx(parents.getIdx())
                .stream()
                .filter(pl -> pl.getIdx().equals(lessonIdx))
                .findFirst()
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION,ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));


        //3. 해당 수업아이디가 있으면 정보 주기
        return GetLessonDetailByParentsResponseDto.of(lesson.getIdx(),lesson.getTeacher().getName(),
                DateAndTimeConvert.dateConvertString(lesson.getStartDate()),lesson.getPayment().getValue(),
                lesson.getAmount(),
                GetLessonDetailByParentsResponseAccount.of(lesson.getAccount().getName(),lesson.getAccount().getBank(), lesson.getAccount().getNumber() ));


    }

    public GetLessonByUserResponseDto getLessonByUser(final Long userIdx){
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if(user.isMatchedRole(Role.PARENTS)){
            return GetLessonByUserResponseDto.of(!(lessonRepository.findAllByParentsIdx(user.getIdx()).isEmpty())
                    ,user.getName());

        } else if (user.isMatchedRole(Role.TEACHER)) {
            return GetLessonByUserResponseDto.of(!(lessonRepository.findAllByTeacherIdx(user.getIdx()).isEmpty())
                    ,user.getName());
        }
        else{
            return null; // 관리자 계정일경우..... 뭐하지??
        }
    }

    @Transactional
    public Long createLesson(
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
        Lesson lesson  = lessonAssembler.toEntity(
                teacher,
                account,
                request.getLesson().getSubject(),
                request.getLesson().getStudentName(),
                request.getLesson().getCount(),
                payment,
                request.getLesson().getAmount(),
                DateAndTimeConvert.stringConvertDate(request.getLesson().getStartDate())

        );

        teacher.addLesson(lesson);
        lessonRepository.save(lesson);


        //2.1 레슨이 선불일 경우 가짜 PaymentRecord 생성
        if(lesson.isMatchedPayment(Payment.PRE_PAYMENT)){
            paymentRecordRepository.save(paymentRecordAssembler.toEntity(lesson, null));
        }


        //3. 해당 레슨 정기일정 생성
        //?근본적인의문 : builder와 어셈블러가 다른이유를 모르겠음?/

        List<RegularSchedule> regularScheduleList = request.getLesson().getRegularScheduleList().stream()
                .map(rs->regularScheduleRepository.save(
                        regularScheduleAssembler.toEntity(
                                lesson,
                                DateAndTimeConvert.stringConvertTime(rs.getStartTime()),
                                DateAndTimeConvert.stringConvertTime(rs.getEndTime()),
                                DayOfWeek.getDayOfWeekByValue(rs.getDayOfWeek())
                        )
                )).collect(Collectors.toList());

        regularScheduleList.forEach(a->{
            lesson.addRegularSchedule(a);
        });




        lesson.getRegularScheduleList().forEach(lrs->System.out.println("수업의 요일들 : "+lrs.getDayOfWeek()));



        //4. 스케쥴 자동생성 <- 이거는 나중에로직 추가
        Schedule.autoCreateSchedule(lesson.getStartDate(),lesson.getCount(),lesson);


        return lesson.getIdx();

    }

    public String createLessonCode(Long lessonIdx){
        //statless하게 lessonIdx의 정보를 가진 레슨코드를 생성하여 추후 레슨코드만 해석해도 어떤 레슨인지 알수있게
        byte[] lessonIdxBytes = (lessonIdx+"").getBytes();
        String lessonCode = Base64.getEncoder().encodeToString(lessonIdxBytes);

        return lessonCode;
    }

    public Long getLessonIdxFromLessonCode(String lessonCode){

        byte[] lessonIdxBytes = Base64.getDecoder().decode(lessonCode);
        Long lessonIdx = Long.parseLong(new String(lessonIdxBytes));

        return lessonIdx;

    }




}
