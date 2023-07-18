package gwasuwonshot.tutice.user.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.config.jwt.JwtService;
import gwasuwonshot.tutice.user.dto.assembler.UserAssembler;
import gwasuwonshot.tutice.user.dto.request.LocalLoginRequestDto;
import gwasuwonshot.tutice.user.dto.request.LocalSignUpRequestDto;
import gwasuwonshot.tutice.user.dto.request.UpdateUserDeviceTokenRequestDto;
import gwasuwonshot.tutice.user.dto.response.LoginResponseDto;
import gwasuwonshot.tutice.user.entity.Provider;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.exception.authException.InvalidPasswordException;
import gwasuwonshot.tutice.user.exception.userException.NotFoundUserException;
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

    public LoginResponseDto localLogin(LocalLoginRequestDto request) {
        // 유저 존재 여부 확인
        User user = userRepository.findByEmailAndProvider(request.getEmail(), Provider.LOCAL)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        // 비밀번호 일치 확인
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidPasswordException(ErrorStatus.INVALID_PASSWORD_EXCEPTION, ErrorStatus.INVALID_PASSWORD_EXCEPTION.getMessage());
        }

        String accessToken = jwtService.issuedAccessToken(String.valueOf(user.getIdx()));
        String refreshToken = jwtService.issuedRefreshToken(String.valueOf(user.getIdx()));

        return LoginResponseDto.of(accessToken, refreshToken, user);

    }

@Transactional
    public void updateUserDeviceToken(Long userIdx, UpdateUserDeviceTokenRequestDto request) {
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        user.setDeviceToken(request.getDeviceToken());
    }
}
