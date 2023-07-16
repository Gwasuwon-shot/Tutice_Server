package gwasuwonshot.tutice.lesson.dto.assembler;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.PaymentRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PaymentRecordAssembler {
    public PaymentRecord toEntity(Lesson lesson, LocalDate date){//default 로 status false
        return PaymentRecord.builder()
                .lesson(lesson)
                .date(date)
                .amount(lesson.getAmount())
                .build();
    }
}
