package gwasuwonshot.tutice.user.service;

import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.config.jwt.JwtService;
import gwasuwonshot.tutice.config.sms.SmsService;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.exception.invalid.InvalidLessonException;
import gwasuwonshot.tutice.lesson.exception.notfound.NotFoundLessonException;
import gwasuwonshot.tutice.lesson.repository.LessonRepository;
import gwasuwonshot.tutice.user.dto.request.*;
import gwasuwonshot.tutice.user.dto.response.GetAccountByLessonResponse;
import gwasuwonshot.tutice.user.dto.response.GetUserNameResponse;
import gwasuwonshot.tutice.user.dto.response.LoginResponse;
import gwasuwonshot.tutice.user.dto.response.ReissueTokenResponse;
import gwasuwonshot.tutice.user.entity.Provider;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.entity.User;
import gwasuwonshot.tutice.user.exception.authException.AlreadyExistEmailException;
import gwasuwonshot.tutice.user.exception.authException.AlreadyExistPhoneNumberException;
import gwasuwonshot.tutice.user.exception.authException.InvalidPasswordException;
import gwasuwonshot.tutice.user.exception.userException.ForbiddenNotificationUserException;
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
    private final SmsService smsService;
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
        User user = userRepository.findByEmailAndProvider(request.getEmail(), Provider.TEMP)
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

    public GetAccountByLessonResponse getAccountByLesson(Long userIdx, Long lessonIdx) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 수업 존재 여부 확인
        Lesson lesson = lessonRepository.findByIdxAndDeletedAtIsNull(lessonIdx)
                .orElseThrow(() -> new NotFoundLessonException(ErrorStatus.NOT_FOUND_LESSON_EXCEPTION, ErrorStatus.NOT_FOUND_LESSON_EXCEPTION.getMessage()));
        // 수업과 유저 연결 여부 확인
        if (!lesson.isMatchedUser(user))
            throw new InvalidLessonException(ErrorStatus.INVALID_LESSON_EXCEPTION, ErrorStatus.INVALID_LESSON_CODE_EXCEPTION.getMessage());

        return GetAccountByLessonResponse.of(lesson.getAccount());
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

    public void sendValidationNumber(SendValidationNumberRequest request) {
        // 전화번호 중복 확인
        if(userRepository.existsByPhoneNumber(request.getPhone())) throw new AlreadyExistPhoneNumberException(ErrorStatus.ALREADY_EXIST_PHONE_NUMBER_EXCEPTION, ErrorStatus.ALREADY_EXIST_PHONE_NUMBER_EXCEPTION.getMessage());
        smsService.sendSMS(request.getPhone());
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findBySocialTokenAndProvider(request.getSocialToken(), Provider.getProviderByValue(request.getProvider()))
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        String accessToken = jwtService.issuedAccessToken(String.valueOf(user.getIdx()));
        String refreshToken = jwtService.issuedRefreshToken(String.valueOf(user.getIdx()));

        return LoginResponse.of(accessToken, refreshToken, user);
    }

    public boolean isUser(LoginRequest request) {
        // 이탈 유저 체크까지
        return userRepository.existsBySocialTokenAndProviderAndNameIsNotNull(request.getSocialToken(), Provider.getProviderByValue(request.getProvider()));
    }

    public LoginResponse tempSignUp(LoginRequest request) {
        User newUser = userRepository.findBySocialTokenAndProvider(request.getSocialToken(), Provider.getProviderByValue(request.getProvider()))
                .orElseGet(() -> userRepository.save(User.toEntity(request.getSocialToken(), Provider.getProviderByValue(request.getProvider()))));

        String accessToken = jwtService.issuedAccessToken(String.valueOf(newUser.getIdx()));
        String refreshToken = jwtService.issuedRefreshToken(String.valueOf(newUser.getIdx()));

        return LoginResponse.of(accessToken, refreshToken);
    }

    @Transactional
    public LoginResponse signUp(Long userIdx, SignUpRequest request) {

        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        // 전화번호 중복 확인
        if(userRepository.existsByPhoneNumberAndProviderNot(request.getPhone(), Provider.TEMP_PARENTS)) throw new AlreadyExistPhoneNumberException(ErrorStatus.ALREADY_EXIST_PHONE_NUMBER_EXCEPTION, ErrorStatus.ALREADY_EXIST_PHONE_NUMBER_EXCEPTION.getMessage());
        // TEMP_PARENTS 인 경우
        if(userRepository.existsByPhoneNumberAndProvider(request.getPhone(), Provider.TEMP_PARENTS)){
            User existingUser = userRepository.findByPhoneNumberAndProvider(request.getPhone(), Provider.TEMP_PARENTS);
            existingUser.updateSocialInfo(user.getProvider(), user.getSocialToken());
            userRepository.saveAndFlush(existingUser);
            userRepository.delete(user);
            user = existingUser;
        }
        // 정보 업데이트
        user.updateInfo(request.getName(), Role.getRoleByValue(request.getRole()), request.getPhone(), request.getIsMarketing());

        String accessToken = jwtService.issuedAccessToken(String.valueOf(user.getIdx()));
        String refreshToken = jwtService.issuedRefreshToken(String.valueOf(user.getIdx()));

        return LoginResponse.of(accessToken, refreshToken, user);
    }

    public void getNotificationStatus(Long userIdx) {
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        if(user.getDeviceToken() == null || user.getDeviceToken().isBlank()) throw new ForbiddenNotificationUserException(ErrorStatus.FORBIDDEN_NOTIFICATION_USER_EXCEPTION, ErrorStatus.FORBIDDEN_NOTIFICATION_USER_EXCEPTION.getMessage());
    }

    public ReissueTokenResponse reissueToken(ReissueTokenRequest request) {
        Long userIdx = jwtService.getUserIdx();
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new NotFoundUserException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
       jwtService.checkTokenExpiryTime(userIdx, request.getRefreshToken());

       String accessToken = jwtService.issuedAccessToken(String.valueOf(user.getIdx()));
       String refreshToken = jwtService.issuedRefreshToken(String.valueOf(user.getIdx()));
       return ReissueTokenResponse.of(accessToken, refreshToken);
    }
}
