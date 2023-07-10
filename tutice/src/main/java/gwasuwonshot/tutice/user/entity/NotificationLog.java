package gwasuwonshot.tutice.user.entity;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;

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
}
