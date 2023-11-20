package gwasuwonshot.tutice.user.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.config.jwt.JwtService;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonAccountResponse;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.exception.invalid.InvalidLessonException;
import gwasuwonshot.tutice.lesson.exception.notfound.NotFoundLessonException;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.user.dto.request.CheckDuplicationEmailRequest;
import gwasuwonshot.tutice.user.dto.request.LocalLoginRequest;
import gwasuwonshot.tutice.user.dto.request.LocalSignUpRequest;
import gwasuwonshot.tutice.user.dto.request.UpdateUserDeviceTokenRequest;
import gwasuwonshot.tutice.user.dto.response.GetUserNameResponse;
import gwasuwonshot.tutice.user.dto.response.LoginResponse;
import gwasuwonshot.tutice.user.entity.Provider;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.exception.authException.AlreadyExistEmailException;
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
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    public LoginResponse localSignUp(LocalSignUpRequest request) {
        // 이메일 중복 시, 에러 발생
        if(userRepository.existsByEmail(request.getEmail())) throw new AlreadyExistEmailException(ErrorStatus.ALREADY_EXIST_EMAIL_EXCEPTION, ErrorStatus.ALREADY_EXIST_EMAIL_EXCEPTION.getMessage());
        // 비밀번호 암호화
        String password = passwordEncoder.encode(request.getPassword());

        User user = User.toEntity(
                request.getEmail(),
                password,
                request.getName(),

                Role.getRoleByValue(request.getRole()),
                request.getIsMarketing());
        User newUser = userRepository.save(user);

        String accessToken = jwtService.issuedAccessToken(String.valueOf(newUser.getIdx()));
        String refreshToken = jwtService.issuedRefreshToken(String.valueOf(newUser.getIdx()));

        return LoginResponse.of(accessToken, refreshToken, newUser);

    }

    public LoginResponse localLogin(LocalLoginRequest request) {
        // 유저 존재 여부 확인
        User user = userRepository.findByEmailAndProvider(request.getEmail(), Provider.LOCAL)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        // 비밀번호 일치 확인
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidPasswordException(ErrorStatus.INVALID_PASSWORD_EXCEPTION, ErrorStatus.INVALID_PASSWORD_EXCEPTION.getMessage());
        }

        String accessToken = jwtService.issuedAccessToken(String.valueOf(user.getIdx()));
        String refreshToken = jwtService.issuedRefreshToken(String.valueOf(user.getIdx()));

        return LoginResponse.of(accessToken, refreshToken, user);

    }

    @Transactional
    public void updateUserDeviceToken(Long userIdx, UpdateUserDeviceTokenRequest request) {
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        user.setDeviceToken(request.getDeviceToken());
    }

    public GetUserNameResponse getUserName(Long userIdx) {
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        return GetUserNameResponse.of(user.getName());
    }

    public GetLessonAccountResponse getAccountByLesson(Long userIdx, Long lessonIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findById(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));
        // 수업과 유저 연결 여부 확인
        if (!lesson.isMatchedUser(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());

        return GetLessonAccountResponse.of(lesson.getAccount());
    }

    public void checkDuplicationEmail(CheckDuplicationEmailRequest request) {
        // 이메일 중복 시, 에러 발생
        if(userRepository.existsByEmail(request.getEmail())) throw new AlreadyExistEmailException(ErrorStatus.ALREADY_EXIST_EMAIL_EXCEPTION, ErrorStatus.ALREADY_EXIST_EMAIL_EXCEPTION.getMessage());
    }


    @Transactional
    public void logout(Long userIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        jwtService.logout(userIdx);
    }
}
