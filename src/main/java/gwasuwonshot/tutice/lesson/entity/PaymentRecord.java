package gwasuwonshot.tutice.lesson.entity;

import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import gwasuwonshot.tutice.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRecord extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "lesson_idx", nullable = false,  foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Lesson lesson;

    private LocalDate date=null;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Boolean status = false;

    @Builder
    public PaymentRecord(Lesson lesson, Long amount, Boolean status){
        this.lesson = lesson;
        this.amount = amount;
        this.status = false;

    }

    public void recordDate(LocalDate date) {
        this.date=date;
        this.status=true;
    }

    public Boolean isRecorded(){
        return this.status;
    }


}
