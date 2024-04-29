package gwasuwonshot.tutice.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetNaverProfileInfoResponse {
    private String resultcode;
    private String message;
    private ResponseData response;

    public static class ResponseData {
        @JsonProperty("email")
        private String email;
        @JsonProperty("nickname")
        private String nickname;
        @JsonProperty("profile_image")
        private String profileImage;
        @JsonProperty("age")
        private String age;
        @JsonProperty("gender")
        private String gender;
        @JsonProperty("id")
        private String id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("birthday")
        private String birthday;
        @JsonProperty("birthyear")
        private String birthyear;
        @JsonProperty("mobile")
        private String mobile;
    }
}

