package gwasuwonshot.tutice.lesson.service;


import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.dto.assembler.LessonAssembler;
import gwasuwonshot.tutice.lesson.dto.assembler.PaymentRecordAssembler;
import gwasuwonshot.tutice.lesson.dto.assembler.RegularScheduleAssembler;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordView.GetPaymentRecordViewCycle;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordView.GetPaymentRecordViewLesson;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.PaymentRecord;
import gwasuwonshot.tutice.lesson.entity.RegularSchedule;
import gwasuwonshot.tutice.lesson.exception.invalid.InvalidLessonException;
import gwasuwonshot.tutice.lesson.exception.invalid.InvalidPaymentRecordException;
import gwasuwonshot.tutice.lesson.exception.notfound.NotFoundLessonException;
import gwasuwonshot.tutice.lesson.exception.notfound.NotFoundPaymentRecordException;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.lesson.repository.PaymentRecordRepository;
import gwasuwonshot.tutice.lesson.repository.RegularScheduleRepository;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.entity.ScheduleStatus;
import gwasuwonshot.tutice.schedule.repository.ScheduleRepository;
import gwasuwonshot.tutice.user.dto.assembler.AccountAssembler;
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
import java.util.Collections;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class PaymentRecordService {
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final LessonAssembler lessonAssembler;
    private final AccountAssembler accountAssembler;
    private final AccountRepository accountRepository;
    private final RegularScheduleAssembler regularScheduleAssembler;
    private final RegularScheduleRepository regularScheduleRepository;
    private final PaymentRecordAssembler paymentRecordAssembler;
    private final PaymentRecordRepository paymentRecordRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public GetPaymentRecordViewLesson getPaymentRecordView(Long userIdx, Long lessonIdx, Long paymentRecordIdx){
        //1. 유저가 선생님인지 확인
        User teacher = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if(!teacher.isMatchedRole(Role.TEACHER)){
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }

        //2. 파라미터로 들어오는 lessonIdx가 존재하는지 확인
        Lesson lesson=lessonRepository.findById(lessonIdx)
                .orElseThrow(()-> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));

        //3. 이 레슨이 선생님의 레슨인지 확인
        if(!lesson.isMatchedTeacher(teacher)){
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION,ErrorStatus.INVALID_LESSON_EXCEPTION.getMessage());

        }
        // 해당레슨의 사이클 확인,  레슨 사이클의 처음스케쥴데이트 가장 늦은 스케쥴데이트 가져오기
        Long cycle;
        if(paymentRecordIdx==null){
            cycle=lesson.getCycle();
        }
        else {
            //레슨과 페이먼트레코드가 연결된것인지확인

            System.out.println(paymentRecordIdx);
            PaymentRecord paymentRecord = paymentRecordRepository.findById(paymentRecordIdx)
                    .orElseThrow(() -> new NotFoundPaymentRecordException(ErrorStatus.NOT_FOUND_PAYMENT_RECORD_EXCEPTION,ErrorStatus.NOT_FOUND_PAYMENT_RECORD_EXCEPTION.getMessage()));

            //들아오는 paymentRecord로 사이클판단
            lesson.getPaymenRecordList().forEach((a)->{
                System.out.println("정렬전 : "+a.getCreatedAt());
            });

            //regularScheduleList를 요일순서로 정렬
            Collections.sort( lesson.getPaymenRecordList(), new Comparator<PaymentRecord>() {
                @Override
                public int compare(PaymentRecord o1, PaymentRecord o2) {
                    //정렬목표 : 오름차순 : 오래된순
                    if(o1.getCreatedAt().isAfter(o2.getCreatedAt())){
                        return 1;
                    }
                    return -1;
                }
            });
            lesson.getPaymenRecordList().forEach((a)->{
                System.out.println("정렬후 : "+a.getCreatedAt());
            });


            cycle = Long.valueOf(lesson.getPaymenRecordList().indexOf(paymentRecord))+1;
            System.out.println(cycle);

            if(cycle == -1){
                throw new InvalidPaymentRecordException(ErrorStatus.UNCONNECTED_LESSON_PAYMENT_RECORD_EXCEPTION,ErrorStatus.UNCONNECTED_LESSON_PAYMENT_RECORD_EXCEPTION.getMessage());
            }


        }
        Schedule endSchedule = scheduleRepository.findTopByLessonAndCycleAndStatusNotOrderByDateDesc(lesson,cycle, ScheduleStatus.CANCEL);
        Schedule startSchedule = scheduleRepository.findTopByLessonAndCycleAndStatusNotOrderByDateAsc(lesson,cycle, ScheduleStatus.CANCEL);

        return GetPaymentRecordViewLesson.of(lesson.getIdx(), lesson.getStudentName(), lesson.getSubject(),
                GetPaymentRecordViewCycle.of(cycle,
                        DateAndTimeConvert.localDateConvertString(startSchedule.getDate()),
                        DateAndTimeConvert.localDateConvertString(endSchedule.getDate())));

    }



    @Transactional
    public void updatePaymentRecord(Long userIdx, Long paymentRecordIdx, LocalDate paymentDate){
//        유저의 역할이 선생님이 맞나요?
        User teacher = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if(!teacher.isMatchedRole(Role.TEACHER)){
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION,ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }
//        들어오는 페이먼트레코드 아이디가 존재하고 해당 레코드의 레슨이 선생님것이 맞나요?

        PaymentRecord paymentRecord =paymentRecordRepository.findById(paymentRecordIdx)
                .orElseThrow(()-> new NotFoundPaymentRecordException(ErrorStatus.NOT_FOUND_PAYMENT_RECORD_EXCEPTION, ErrorStatus.NOT_FOUND_PAYMENT_RECORD_EXCEPTION.getMessage()));

        //3. 이 레슨이 선생님의 레슨인지 확인
        if(!paymentRecord.getLesson().isMatchedTeacher(teacher)){
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION,ErrorStatus.INVALID_LESSON_EXCEPTION.getMessage());
        }

        // paymentRecord가 이미 입금되었는지 여부
        if(paymentRecord.isRecorded()){
            throw new InvalidPaymentRecordException(ErrorStatus.INVALID_PAYMENT_RECORD_EXCEPTION,ErrorStatus.INVALID_PAYMENT_RECORD_EXCEPTION.getMessage());
        }


//                페이먼트레코드의 date update

        paymentRecord.recordDate(paymentDate);

    }
}


