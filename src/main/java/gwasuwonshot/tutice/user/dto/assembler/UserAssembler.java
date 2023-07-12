package gwasuwonshot.tutice.user.dto.assembler;

import gwasuwonshot.tutice.user.entity.Provider;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    public User toEntity(String email, Provider provider, String password,
                         String name, String deviceToken, Role role, Boolean isMarketing){
        return User.builder()
                .email(email)
                .provider(provider)
                .password(password)
                .name(name)
                .deviceToken(deviceToken)
                .role(role)
                .isMarketing(isMarketing)
                .build();

    }


}
