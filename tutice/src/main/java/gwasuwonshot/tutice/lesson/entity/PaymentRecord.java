package gwasuwonshot.tutice.lesson.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "lesson_idx")
    private Lesson lesson;

    @Column(nullable = false)
    private Date date;

    @Builder
    public PaymentRecord(Lesson lesson, Date date){
        this.lesson = lesson;
        this.date = date;

    }


}
