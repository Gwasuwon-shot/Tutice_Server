package gwasuwonshot.tutice.lesson.entity;

import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRecord extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "lesson_idx", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Boolean status = false;

    @Builder
    public PaymentRecord(Lesson lesson, Date date, Long amount, Boolean status){
        this.lesson = lesson;
        this.date = date;
        this.amount = amount;
        this.status = false;

    }


}
