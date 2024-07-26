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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
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
    private LocalDate startDate;

    @Column(nullable = false)
    private Boolean isFinished = false;

    @OneToMany(mappedBy = "lesson")
    private List<PaymentRecord> paymenRecordList;


    @OneToMany(mappedBy = "lesson")
    private List<RegularSchedule> regularScheduleList;

    @OneToMany(mappedBy = "lesson")
    private List<Schedule> scheduleList;

    public static Lesson toEntity(User teacher, Account account, String subject, String studentName,
                                  Long count, Payment payment, Long amount, LocalDate startDate) {
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
                  Long count, Payment payment, Long amount, LocalDate startDate){
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


    public void connectParents(User parents) {
        this.parents=parents;
    }


    public Long getCycle(){
        if(this.isMatchedPayment(Payment.PRE_PAYMENT)){
            return Long.valueOf(this.getPaymenRecordList().size());
        }
        return Long.valueOf(this.getPaymenRecordList().size()+1);


    }
    public String createLessonCode(){
        //statless하게 lessonIdx의 정보를 가진 레슨코드를 생성하여 추후 레슨코드만 해석해도 어떤 레슨인지 알수있게
        byte[] lessonIdxBytes = (this.getIdx()+"").getBytes();
        String lessonCode = Base64.getEncoder().encodeToString(lessonIdxBytes);

        return lessonCode;
    }
    public void finishLesson(){this.isFinished=true;}

    public void deleteLesson(){ super.markAsDeleted();}

    public Boolean isMatchedParents(User parents){
        if(this.getParents() == null ){return false;}
        return this.getParents().equals(parents);
    }

    public Boolean isMatchedTeacher(User teacher){
        return this.getTeacher().equals(teacher);
    }

    public Boolean isMatchedPayment(Payment matchedPayment){
        return this.getPayment().equals(matchedPayment);
    }


    public boolean isMatchedUser(User user) {
        return isMatchedTeacher(user) || isMatchedParents(user);
    }
}
