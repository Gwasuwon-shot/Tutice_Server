package gwasuwonshot.tutice.external.firebase.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import gwasuwonshot.tutice.external.firebase.domain.FCMMessage;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FCMService {

    @Value("${firebase.api-url}")
    private final String API_URL;
    // TODO : constants들 class 내부 상수로 정리

    @Value("${firebase.config-path}")
    private final String FIREBASE_CONFIG_PATH;


    private final ObjectMapper objectMapper;

    public Response sendMessage(String deviceToken,String title, String body) throws IOException {
        OkHttpClient client = new OkHttpClient();
        FCMMessage message = FCMMessage.makeMessage(deviceToken, title, body);
        RequestBody requestBody = RequestBody.create(objectMapper.writeValueAsString(message),
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        return client.newCall(request).execute();
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}

