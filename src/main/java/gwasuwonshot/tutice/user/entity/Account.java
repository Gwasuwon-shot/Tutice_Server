package gwasuwonshot.tutice.user.entity;

import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import gwasuwonshot.tutice.lesson.entity.DayOfWeek;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "techer_idx", nullable = false,  foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private User teacher;

    @Column(nullable = false)
    private String name;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Bank bank;

    @Column(nullable = false)
    private String number;


    @Builder Account( User teacher, String name, Bank bank, String number){
        this.teacher=teacher;
        this.name=name;
        this.bank=bank;
        this.number=number;

    }

    public static Account toEntity(User teacher, String name, Bank bank, String number) {
        return Account.builder()
                .teacher(teacher)
                .name(name)
                .bank(bank)
                .number(number)
                .build();
    }
}
