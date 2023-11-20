package gwasuwonshot.tutice.lesson.service;


import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordCycle.GetPaymentRecordCycleResponse;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordByLesson.GetPaymentRecordResponse;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.Payment;
import gwasuwonshot.tutice.lesson.entity.PaymentRecord;
import gwasuwonshot.tutice.lesson.exception.invalid.InvalidLessonException;
import gwasuwonshot.tutice.lesson.exception.invalid.InvalidPaymentRecordException;
import gwasuwonshot.tutice.lesson.exception.notfound.NotFoundLessonException;
import gwasuwonshot.tutice.lesson.exception.notfound.NotFoundPaymentRecordException;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.lesson.repository.PaymentRecordRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentRecordService {
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final ScheduleRepository scheduleRepository;



    public List<GetPaymentRecordResponse> getPaymentRecordByLesson(Long userIdx, Long lessonIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findByIdxAndDeletedAtIsNull(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));
        // 수업과 유저 연결 여부 확인
        if (!lesson.isMatchedUser(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());

        // 최신 순으로 정렬
        Collections.sort(lesson.getPaymenRecordList(), new Comparator<PaymentRecord>() {
            @Override
            public int compare(PaymentRecord o1, PaymentRecord o2) {
                //정렬목표 : 내림차순 : 최신순
                if (o1.getCreatedAt().isAfter(o2.getCreatedAt())) {
                    return -1;
                }
                return 1;
            }
        });

        List<GetPaymentRecordResponse> paymentRecordList = new ArrayList<>();
        if(lesson.isMatchedPayment(Payment.PRE_PAYMENT)){
            //선불
            //- [ ] 사이클개수대로 payment 가져오기
            lesson.getPaymenRecordList()
                    .forEach(pr -> {
                        paymentRecordList.add(
                                GetPaymentRecordResponse.of(
                                        pr.getIdx(),
                                        (pr.getDate() == null) ? null : DateAndTimeConvert.localDateConvertString(pr.getDate()),
                                        pr.getAmount(), pr.getStatus()));
                    });
        }else {
            //후불
            lesson.getPaymenRecordList().subList(0, lesson.getCycle().intValue() - 1)
                    .forEach(pr -> {
                                paymentRecordList.add(
                                        GetPaymentRecordResponse.of(
                                                pr.getIdx(),
                                                (pr.getDate() == null) ? null : DateAndTimeConvert.localDateConvertString(pr.getDate()),
                                                pr.getAmount(), pr.getStatus()));
                            }
                    );
        }
        return paymentRecordList;
    }

    public GetPaymentRecordCycleResponse getPaymentRecordCycle(Long userIdx, Long paymentRecordIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 입금 내역 존재 여부 확인
        PaymentRecord paymentRecord = paymentRecordRepository.findById(paymentRecordIdx)
                .orElseThrow(() -> new NotFoundPaymentRecordException(ErrorStatus.NOT_FOUND_PAYMENT_RECORD_EXCEPTION, ErrorStatus.NOT_FOUND_PAYMENT_RECORD_EXCEPTION.getMessage()));
        Lesson lesson = paymentRecord.getLesson();
        // 수업과 선생님 연결 여부 확인
        if (!lesson.isMatchedTeacher(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());

        // 해당레슨의 사이클 확인,  레슨 사이클의 처음스케쥴데이트 가장 늦은 스케쥴데이트 가져오기
        //regularScheduleList를 요일순서로 정렬
        Collections.sort(lesson.getPaymenRecordList(), new Comparator<PaymentRecord>() {
            @Override
            public int compare(PaymentRecord o1, PaymentRecord o2) {
                //정렬목표 : 오름차순 : 오래된순
                if (o1.getCreatedAt().isAfter(o2.getCreatedAt())) {
                    return 1;
                }
                return -1;
            }
        });

        // 들아오는 paymentRecord로 사이클판단
        Long cycle = Long.valueOf(lesson.getPaymenRecordList().indexOf(paymentRecord)) + 1;

        if (cycle == -1) {
            throw new InvalidPaymentRecordException(ErrorStatus.UNCONNECTED_LESSON_PAYMENT_RECORD_EXCEPTION, ErrorStatus.UNCONNECTED_LESSON_PAYMENT_RECORD_EXCEPTION.getMessage());
        }
        Schedule endSchedule = scheduleRepository.findTopByLessonAndCycleAndStatusNotOrderByDateDesc(lesson, cycle, ScheduleStatus.CANCEL);
        Schedule startSchedule = scheduleRepository.findTopByLessonAndCycleAndStatusNotOrderByDateAsc(lesson, cycle, ScheduleStatus.CANCEL);

        return GetPaymentRecordCycleResponse.of(paymentRecord.getIdx(), cycle, startSchedule, endSchedule);
    }



    @Transactional
    public void updatePaymentRecord(Long userIdx, Long paymentRecordIdx, LocalDate paymentDate) {
//        유저의 역할이 선생님이 맞나요?
        User teacher = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if (!teacher.isMatchedRole(Role.TEACHER)) {
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION, ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }
//        들어오는 페이먼트레코드 아이디가 존재하고 해당 레코드의 레슨이 선생님것이 맞나요?

        PaymentRecord paymentRecord = paymentRecordRepository.findById(paymentRecordIdx)
                .orElseThrow(() -> new NotFoundPaymentRecordException(ErrorStatus.NOT_FOUND_PAYMENT_RECORD_EXCEPTION, ErrorStatus.NOT_FOUND_PAYMENT_RECORD_EXCEPTION.getMessage()));

        //3. 이 레슨이 선생님의 레슨인지 확인
        if (!paymentRecord.getLesson().isMatchedTeacher(teacher)) {
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_EXCEPTION.getMessage());
        }

        // paymentRecord가 이미 입금되었는지 여부
        if (paymentRecord.isRecorded()) {
            throw new InvalidPaymentRecordException(ErrorStatus.INVALID_PAYMENT_RECORD_EXCEPTION, ErrorStatus.INVALID_PAYMENT_RECORD_EXCEPTION.getMessage());
        }


//                페이먼트레코드의 date update

        paymentRecord.recordDate(paymentDate);

    }
}






