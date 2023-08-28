package gwasuwonshot.tutice.lesson.service;


import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.dto.response.GetPaymentRecordCycleResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getPaymentRecord.*;
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


    @Transactional
    public GetPaymentRecordByUserResponseDto getLessonPaymentRecordByUser(Role role, Long userIdx, Long lessonIdx) {
        // 유저의 역할이 부모님이 맞나요?
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if (!user.isMatchedRole(role)) {
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION, ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());
        }

        // 레슨의 존재확인
        Lesson lesson = lessonRepository.findById(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));



        // 레슨과 유저의 연결성확인
        // TODO if문 depth 리팩필요
        if(role.equals(Role.TEACHER)){
            if (!lesson.isMatchedTeacher(user)) {
                throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());
            }
        } else if (role.equals(Role.PARENTS)) {

            if (!lesson.isMatchedParents(user)) {
                throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());
            }

        } else {
            throw new InvalidRoleException(ErrorStatus.INVALID_ROLE_EXCEPTION, ErrorStatus.INVALID_ROLE_EXCEPTION.getMessage());

        }

//
//        // 레슨의 현재 사이클 확인
//        //    스케쥴테이블에서 현재사이클의 가장 최신 스케쥴이 출결이면(상태없음이 아니면)(어차피 취소는 생각안해도됨�) ->사이클이마무리된거로판단
//
//        Schedule lastestSchedule = scheduleRepository.findTopByLessonAndCycleAndStatusNotOrderByDateDesc(lesson, lesson.getCycle(), ScheduleStatus.CANCEL);
//

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


        List<GetPaymentRecord> paymentRecordList = new ArrayList<>();

        if(lesson.isMatchedPayment(Payment.PRE_PAYMENT)){
            //선불
            //- [ ] 사이클개수대로 payment 가져오기
            lesson.getPaymenRecordList()
                    .forEach(pr -> {
                        paymentRecordList.add(
                                GetPaymentRecord.of(
                                        pr.getIdx(),
                                        (pr.getDate() == null) ? null : DateAndTimeConvert.localDateConvertString(pr.getDate()),
                                        pr.getAmount(), pr.getStatus()));
            });

        }else {
            //후불
            lesson.getPaymenRecordList().subList(0, lesson.getCycle().intValue() - 1)
                    .forEach(pr -> {
                                paymentRecordList.add(
                                        GetPaymentRecord.of(
                                                pr.getIdx(),
                                                (pr.getDate() == null) ? null : DateAndTimeConvert.localDateConvertString(pr.getDate()),
                                                pr.getAmount(), pr.getStatus()));
                            }
                    );
        }

//        if (lastestSchedule.isMatchedStatus(ScheduleStatus.NO_STATUS)) {
//            // - [ ] 아니면, 사이클-1개수로 가져오기
//            lesson.getPaymenRecordList().subList(0, lesson.getCycle().intValue() - 1)
//                    .forEach(pr -> {
//                                paymentRecordList.add(
//                                        GetPaymentRecord.of(
//                                                pr.getIdx(), (pr.getDate() == null) ? null : DateAndTimeConvert.localDateConvertString(pr.getDate()), pr.getAmount(), pr.getStatus()));
//                            }
//                    );
//        } else {
//            //- [ ] 사이클개수대로 payment 가져오기
//            lesson.getPaymenRecordList().forEach(pr -> {
//                paymentRecordList.add(
//                        GetPaymentRecord.of(
//                                pr.getIdx(), DateAndTimeConvert.localDateConvertString(pr.getDate()), pr.getAmount(), pr.getStatus()));
//            });
//        }

        if (role.equals(Role.TEACHER)) {
            return GetPaymentRecordByTeacherResponseDto.of(
                    GetPaymentRecordLessonByTeacher.of(
                            lesson.getIdx(), lesson.getStudentName(), lesson.getSubject()),
                    DateAndTimeConvert.nowLocalDateConvertString(),
                    paymentRecordList
            );

        } else if (role.equals(Role.PARENTS)) {
            return GetPaymentRecordByParentsResponseDto.of(
                    GetPaymentRecordLessonByParents.of(
                            lesson.getIdx(), lesson.getStudentName(), lesson.getTeacher().getName(), lesson.getSubject()),
                    DateAndTimeConvert.nowLocalDateConvertString(),
                    paymentRecordList
            );

        }

        return null;


    }

    public GetPaymentRecordCycleResponseDto getPaymentRecordCycle(Long userIdx, Long paymentRecordIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 입금 내역 존재 여부 확인
        PaymentRecord paymentRecord = paymentRecordRepository.findById(paymentRecordIdx)
                .orElseThrow(() -> new NotFoundPaymentRecordException(ErrorStatus.NOT_FOUND_PAYMENT_RECORD_EXCEPTION, ErrorStatus.NOT_FOUND_PAYMENT_RECORD_EXCEPTION.getMessage()));
        Lesson lesson = paymentRecord.getLesson();
        // 수업과 유저 연결 여부 확인
        if (!user.equals(lesson.getParents()) && !user.equals(lesson.getTeacher()))
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

        return GetPaymentRecordCycleResponseDto.of(paymentRecord.getIdx(), cycle, startSchedule, endSchedule);
    }
}






