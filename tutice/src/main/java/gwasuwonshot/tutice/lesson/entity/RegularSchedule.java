package gwasuwonshot.tutice.lesson.entity;


import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegularSchedule extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "lesson_idx", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private Time startTime;

    @Column(nullable = false)
    private Time endTime;


    @Builder
    public RegularSchedule(Lesson lesosn, Time startTime, Time endTime){
        this.lesson = lesosn;
        this.startTime=startTime;
        this.endTime=endTime;

    }
}
