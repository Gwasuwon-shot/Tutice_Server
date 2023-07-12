package gwasuwonshot.tutice.user.dto.assembler;

import gwasuwonshot.tutice.user.entity.Account;
import gwasuwonshot.tutice.user.entity.Bank;
import gwasuwonshot.tutice.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AccountAssembler {

    public Account toEntity(User teacher, String name, String bank, String number){
        return Account.builder()
                .teacher(teacher)
                .name(name)
                .bank(bank)
                .number(number)
                .build();
    }
}
