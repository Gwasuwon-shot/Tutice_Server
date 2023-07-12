package gwasuwonshot.tutice.user.entity;

import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
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
    @JoinColumn(name = "techer_idx", nullable = false)
    private User teacher;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private String number;


    @Builder Account( User teacher, String name, String bank, String number){
        this.teacher=teacher;
        this.name=name;
        this.bank=bank;
        this.number=number;

    }

}
