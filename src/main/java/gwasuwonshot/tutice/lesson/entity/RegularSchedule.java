package gwasuwonshot.tutice.lesson.entity;


import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import gwasuwonshot.tutice.lesson.entity.DayOfWeek;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegularSchedule extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "lesson_idx", nullable = false,  foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Lesson lesson;

    @Column(nullable = false)
    private Time startTime;

    @Column(nullable = false)
    private Time endTime;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    DayOfWeek dayOfWeek;


    @Builder
    public RegularSchedule(Lesson lesson, Time startTime, Time endTime,DayOfWeek dayOfWeek){
        this.lesson = lesson;
        this.startTime=startTime;
        this.endTime=endTime;
        this.dayOfWeek=dayOfWeek;

    }
}
