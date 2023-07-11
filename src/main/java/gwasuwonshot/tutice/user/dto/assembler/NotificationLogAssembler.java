package gwasuwonshot.tutice.user.dto.assembler;

import gwasuwonshot.tutice.user.entity.NotificationLog;
import gwasuwonshot.tutice.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class NotificationLogAssembler {
    public NotificationLog toEntity(User user, String title, String content){
        return NotificationLog.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }
}
