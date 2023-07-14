package gwasuwonshot.tutice.lesson.service;

import gwasuwonshot.tutice.TuticeApplication;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.dto.assembler.LessonAssembler;
import gwasuwonshot.tutice.lesson.dto.assembler.RegularScheduleAssembler;
import gwasuwonshot.tutice.lesson.dto.request.CreateLessonRequestDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonByUserResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonDetailByParentsResponseDto;
import gwasuwonshot.tutice.lesson.entity.DayOfWeek;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.Payment;
import gwasuwonshot.tutice.lesson.entity.RegularSchedule;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.lesson.repository.RegularScheduleRepository;
import gwasuwonshot.tutice.user.dto.assembler.AccountAssembler;
import gwasuwonshot.tutice.user.entity.Account;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.exception.userException.NotFoundUserException;
import gwasuwonshot.tutice.user.repository.AccountRepository;
import gwasuwonshot.tutice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.StandardSocketOptions;
import java.sql.Time;
import java.util.Arrays;
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

    @Transactional
    public GetLessonDetailByParentsResponseDto getLessonDetailByParents(Long userIdx,Long lessonIdx){
        //1. 먼저 유저를 찾고 유저의 롤이 부모님

    }

    public GetLessonByUserResponseDto getLessonByUser(final Long userIdx){
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if(user.getRole().equals(Role.PARENTS)){
            return GetLessonByUserResponseDto.of(!(lessonRepository.findAllByParentsIdx(user.getIdx()).isEmpty())
                    ,user.getName());

        } else if (user.getRole().equals(Role.TEACHER)) {
            return GetLessonByUserResponseDto.of(!(lessonRepository.findAllByTeacherIdx(user.getIdx()).isEmpty())
                    ,user.getName());
        }
        else{
            return null; // 관리자 계정일경우..... 뭐하지??
        }
    }

    @Transactional
    public Long createLesson(
            final Long userId, final CreateLessonRequestDto request){

        User teacher = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        Payment payment = Payment.getPaymentByValue(request.getLesson().getPayment());

        //1. 선생님 계좌등록
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



        //4. 스케쥴 자동생성 <- 이거는 나중에로직 추가


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
