package gwasuwonshot.tutice.external.firebase;

import gwasuwonshot.tutice.external.firebase.service.FCMService;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/notifications")
@RestController
public class FCMTestController {

    private final FCMService FCMService;

    @PostMapping("")
    public String pushNotification(@RequestBody FCMTestRequestDto request) {
        try {
            Response response =  FCMService.sendMessage(request.getDeviceToken(),"수화제목","수화내용");
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
