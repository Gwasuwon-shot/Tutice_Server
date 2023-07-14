package gwasuwonshot.tutice.lesson.dto.assembler;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.Payment;
import gwasuwonshot.tutice.user.entity.Account;
import gwasuwonshot.tutice.user.entity.User;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;

@Component
public class LessonAssembler {

    public Lesson toEntity(User teacher, Account account, String subject, String studentName,
                           Long count, Payment payment, Long amount, Date startDate){
        return Lesson.builder()
                .teacher(teacher)
                .parents(null)
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


