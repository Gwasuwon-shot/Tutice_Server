package gwasuwonshot.tutice.lesson.dto.assembler;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.Payment;
import gwasuwonshot.tutice.user.entity.Account;
import gwasuwonshot.tutice.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LessonAssembler {

    public Lesson toEntity(User teacher, Account account, String subject, String studentName,
                           Long count, Payment payment, Long amount, LocalDate startDate){
        return Lesson.builder()
                .teacher(teacher)
                .account(account)
                .subject(subject)
                .studentName(studentName)
                .count(count)
                .payment(payment)
                .amount(amount)
                .startDate(startDate)
                .build();
    }
}


