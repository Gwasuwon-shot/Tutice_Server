package gwasuwonshot.tutice.user.entity;

import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationLog extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_idx", nullable = false,  foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private User user;

    // TODO : NotificationConstant 이넘으로 변경
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Builder
    public NotificationLog(User user, String title, String content){
        this.user = user;
        this.title= title;
        this.content=content;

    }

    public static NotificationLog toEntity(User user, String title, String content) {
        return NotificationLog.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }
}
