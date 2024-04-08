package gwasuwonshot.tutice.config.sms;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import net.nurigo.java_sdk.api.Message;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.util.HashMap;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class SmsService {

    private final String PREFIX = "sms:";
    private final String SMS_MESSAGE_START = "[Tutice] 본인 확인 인증 번호는 ";
    private final String SMS_MESSAGE_END = " 입니다.";
    private final int LIMIT_TIME = 5 * 60;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${coolsms.apikey}")
    private String apiKey;

    @Value("${coolsms.apisecret}")
    private String apiSecret;

    @Value("${coolsms.senderNumber}")
    private String senderNumber;

    private String createValidationNumber() {
        Random rand = new Random();
        String randomNum = "";
        for (int i = 0; i < 6; i++) {
            String random = Integer.toString(rand.nextInt(10));
            randomNum += random;
        }
        return randomNum;
    }

    private HashMap<String, String> makeParams(String to, String randomNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", senderNumber);
        params.put("type", "SMS");
        params.put("app_version", "test app 1.2");
        params.put("to", to);
        params.put("text", SMS_MESSAGE_START + randomNum + SMS_MESSAGE_END);
        return params;
    }

    // 인증번호 전송하기
    public void sendSMS(String phoneNumber) {
        Message coolsms = new Message(apiKey, apiSecret);
        // 랜덤한 인증 번호 생성
        String validationNumber = createValidationNumber();
        // 발신 정보 설정
        HashMap<String, String> params = makeParams(phoneNumber, validationNumber);
        try {
            // 재전송인 경우
            if(Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + phoneNumber))) redisTemplate.delete(PREFIX + phoneNumber);
            JSONObject obj = (JSONObject) coolsms.send(params);
            redisTemplate.opsForValue().set(PREFIX + phoneNumber, validationNumber, Duration.ofSeconds(LIMIT_TIME));
        } catch (CoolsmsException e) {
            throw new BasicException(ErrorStatus.INTERNAL_SERVER_ERROR, ErrorStatus.INTERNAL_SERVER_ERROR.getMessage());
        }
    }
}
