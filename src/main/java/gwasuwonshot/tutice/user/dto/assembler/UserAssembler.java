package gwasuwonshot.tutice.user.dto.assembler;

import gwasuwonshot.tutice.user.entity.Provider;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    public User toEntity(String email, String password,
                         String name, Role role, Boolean isMarketing){
        return User.builder()
                .email(email)
                .provider(Provider.LOCAL)
                .password(password)
                .name(name)
                .role(role)
                .isMarketing(isMarketing)
                .build();

    }


}
