package gwasuwonshot.tutice.external.firebase.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import gwasuwonshot.tutice.external.firebase.domain.FCMMessage;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
class FCMService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/gwasuoneshot-55659/messages:send";
    // api_url 추후 보안 분리
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
        String firebaseConfigPath = "firebase/gwasuoneshot-firebase.json";
        // firebaseConfigPath 추후 보안 분리
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}

