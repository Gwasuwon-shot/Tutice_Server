package gwasuwonshot.tutice.schedule.entity;

import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "lesson_idx", nullable = false,  foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Lesson lesson;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status = ScheduleStatus.NO_STATUS; //기본값 상태없음은 db의 ddl로 넣기

    @Column(nullable = false)
    private Long cycle;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;


    @Builder
    public Schedule(Lesson lesson, LocalDate date, ScheduleStatus status,
                    Long cycle, LocalTime startTime, LocalTime endTime){
        this.lesson=lesson;
        this.date = date;
        this.status = status;
        this.cycle=cycle;
        this.startTime = startTime;
        this.endTime=endTime;
    }
}
