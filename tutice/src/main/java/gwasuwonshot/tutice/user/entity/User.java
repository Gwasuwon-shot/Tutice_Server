package gwasuwonshot.tutice.user.entity;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table( //두개 칼럼 조합 유니크
//        name="user",
//        uniqueConstraints={
//                @UniqueConstraint(
//                        name={"UniqueEmailAndProvider"},
//                        columnNames={"email", "provider"}
//                )
//        }
//)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private Provider provider;

    private String password;

    @Column(nullable = false)
    private String name;

    private String deviceToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "teacher")
    private List<Account> accountList;

    @OneToMany(mappedBy = "teacher")
    private List<Lesson> lessonList;

    @OneToMany(mappedBy = "user")
    private List<NotificationLog> notificationLogList;

    public void addAccount(Account account){
        accountList.add(account);
    }

    public void addLesson(Lesson lesson){
        lessonList.add(lesson);
    }

    public void addNotificationLog(NotificationLog notificationLog){
        notificationLogList.add(notificationLog);
    }

    @Builder
    public User(String email, Provider provider, String password,
                String name, String deviceToken, Role role){
        this.email = email;
        this.provider = provider;
        this.password=password;
        this.name = name;
        this.deviceToken = deviceToken;
        this.role=role;
        this.accountList=new ArrayList<>();
        this.lessonList=new ArrayList<>();
        this.notificationLogList = new ArrayList<>();
    }


}
