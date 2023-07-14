package gwasuwonshot.tutice.lesson.entity;

import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import gwasuwonshot.tutice.user.entity.Account;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.schedule.entity.Schedule;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "teacher_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private User teacher;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parents_idx" ,foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private User parents;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "account_idx",  foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Account account;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private Long count;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Payment payment;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Date startDate;

    @OneToMany(mappedBy = "lesson", cascade = {CascadeType.PERSIST})
    private List<PaymentRecord> paymenRecordList;


    @OneToMany(mappedBy = "lesson", cascade = {CascadeType.PERSIST})
    private List<RegularSchedule> regularScheduleList;

    @OneToMany(mappedBy = "lesson", cascade = {CascadeType.PERSIST})
    private List<Schedule> scheduleList;

    public void addPaymentRecord(PaymentRecord paymentRecord){
        paymenRecordList.add(paymentRecord);
    }

    public void addRegularSchedule(RegularSchedule regularSchedule){
        regularScheduleList.add(regularSchedule);
    }
    public void addSchedule(Schedule schedule){
        scheduleList.add(schedule);
    }

    @Builder
    public Lesson(User teacher, User parents, Account account, String subject, String studentName,
                  Long count, Payment payment, Long amount, Date startDate){
        this.teacher = teacher;
        this.parents=parents;
        this.account=account;
        this.subject=subject;
        this.studentName=studentName;
        this.count = count;
        this.payment=payment;
        this.amount=amount;
        this.startDate=startDate;
        this.paymenRecordList= new ArrayList<>();
        this.regularScheduleList = new ArrayList<>();
        this.scheduleList = new ArrayList<>();
    }

}
