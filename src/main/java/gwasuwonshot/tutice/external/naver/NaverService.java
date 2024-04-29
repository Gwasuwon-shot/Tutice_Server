package gwasuwonshot.tutice.external.naver;

import gwasuwonshot.tutice.user.dto.request.GetNaverProfileInfoRequest;
import gwasuwonshot.tutice.user.dto.response.GetNaverProfileInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NaverService {

    private final String HEADER = "Bearer ";
    private final String HTTP_HEADER = "Authorization";
    private final String API_URL = "https://openapi.naver.com/v1/nid/me";
    private RestTemplate restTemplate = new RestTemplate();

    public GetNaverProfileInfoResponse getNaverProfileInfo(GetNaverProfileInfoRequest request) {
        String token = request.getNaverToken();
        String header = HEADER + token;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HTTP_HEADER, header);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 호출 및 응답
        ResponseEntity<GetNaverProfileInfoResponse> responseEntity = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                entity,
                GetNaverProfileInfoResponse.class);

        return responseEntity.getBody();
    }
}
