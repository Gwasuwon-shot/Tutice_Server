package gwasuwonshot.tutice.lesson.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.lesson.dto.assembler.LessonAssembler;
import gwasuwonshot.tutice.lesson.dto.request.CreateLessonRequestDto;
import gwasuwonshot.tutice.lesson.dto.response.CreateLessonResponseDto;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.Payment;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.user.dto.assembler.AccountAssembler;
import gwasuwonshot.tutice.user.entity.Account;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.exception.UserNotFoundException;
import gwasuwonshot.tutice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final LessonAssembler lessonAssembler;
    private final AccountAssembler accountAssembler;

    public Long createLesson(
            final Long userId, final CreateLessonRequestDto request){
        User teacher = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));


        Payment payment = Payment.getPaymentByValue(request.getLesson().getPayment());

        Account account = accountAssembler.toEntity(
                teacher,
                request.getAccount().getName(),
                request.getAccount().getBank(),
                request.getAccount().getNumber());

        teacher.addAccount(account);

        Lesson lesson  = lessonAssembler.toEntity(
                teacher,
                null,
                account,
                request.getLesson().getSubject(),
                request.getLesson().getStudentName(),
                request.getLesson().getCount(),
                payment,
                request.getLesson().getAmount()

        );

        teacher.addLesson(lesson);

        lessonRepository.save(lesson);

        return lesson.getIdx();

    }

    public String createLessonCode(Long lessonIdx){


    }
}
