package gwasuwonshot.tutice.user.entity;

import gwasuwonshot.tutice.common.entity.AuditingTimeEntity;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( //두개 칼럼 조합 유니크
        name="user",
        uniqueConstraints={
                @UniqueConstraint(
                        name="UniqueEmailAndProvider",
                        columnNames={"email", "provider"}
                )
        }
)

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider = Provider.TEMP;

    @Column(nullable = false)
    private String socialToken;

    @Column(nullable = false)
    private String phoneNumber;

    private String password;

    @Column(nullable = false)
    private String name;

    private String deviceToken;

    private Boolean isMarketing;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.REMOVE})
    private List<Account> accountList;

    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.ALL})
    private List<Lesson> lessonList;

    // TODO '부모님'일 때, lessonList가 불러와지지 않는 이슈

    @OneToMany(mappedBy = "user")
    private List<NotificationLog> notificationLogList;

    public static User toEntity(String email, String password, String name, Role role, Boolean isMarketing) {
        return User.builder()
                .email(email)
                .provider(Provider.TEMP)
                .password(password)
                .name(name)
                .role(role)
                .isMarketing(isMarketing)
                .build();
    }

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
                String name, String deviceToken, Role role, Boolean isMarketing){
        this.email = email;
        this.provider = provider;
        this.password=password;
        this.name = name;
        this.deviceToken = deviceToken;
        this.role=role;
        this.isMarketing=isMarketing;
        this.accountList=new ArrayList<>();
        this.lessonList=new ArrayList<>();
        this.notificationLogList = new ArrayList<>();
    }





    public Boolean isMatchedRole(Role matchRole){
        return this.getRole().equals(matchRole);
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
