package gwasuwonshot.tutice.lesson.dto.assembler;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.PaymentRecord;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class PaymentRecordAssembler {
    public PaymentRecord toEntity(Lesson lesson, Date date){
        return PaymentRecord.builder()
                .lesson(lesson)
                .date(date)
                .build();
    }
}