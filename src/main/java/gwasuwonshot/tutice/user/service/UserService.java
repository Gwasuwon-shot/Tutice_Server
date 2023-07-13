package gwasuwonshot.tutice.user.service;

import gwasuwonshot.tutice.config.jwt.JwtService;
import gwasuwonshot.tutice.user.dto.assembler.UserAssembler;
import gwasuwonshot.tutice.user.dto.request.LocalSignUpRequestDto;
import gwasuwonshot.tutice.user.dto.response.LoginResponseDto;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserAssembler userAssembler;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Transactional
    public LoginResponseDto localSignUp(LocalSignUpRequestDto request) {
        String password = passwordEncoder.encode(request.getPassword());

        User user = userAssembler.toEntity(
                request.getEmail(),
                password,
                request.getName(),

                Role.getRoleByValue(request.getRole()),
                request.getIsMarketing());
        User newUser = userRepository.save(user);

        String accessToken = jwtService.issuedAccessToken(String.valueOf(newUser.getIdx()));
        String refreshToken = jwtService.issuedRefreshToken(String.valueOf(newUser.getIdx()));

        return LoginResponseDto.of(accessToken, refreshToken, newUser);

    }
}
