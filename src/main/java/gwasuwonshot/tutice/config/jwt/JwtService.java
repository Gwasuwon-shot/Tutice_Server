package gwasuwonshot.tutice.config.jwt;

import gwasuwonshot.tutice.common.exception.BasicException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final long accessTokenExpiryTime = 5184000;
    private final long refreshTokenExpiryTime = 120 * 60 * 7 * 1000L;
    private final String CLAIM_NAME = "userIdx";

    private final RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder()
                .encodeToString(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Access Token 발급
    public String issuedAccessToken(String userIdx) {
        return issuedToken("access_token", accessTokenExpiryTime, userIdx);
    }

    // Refresh Token 발급
    public String issuedRefreshToken(String userIdx) {
        String refreshToken = issuedToken("refresh_token", refreshTokenExpiryTime, userIdx);
        redisTemplate.opsForValue().set(String.valueOf(userIdx), refreshToken, Duration.ofMillis(refreshTokenExpiryTime));
        return refreshToken;
    }

    // JWT 토큰 발급
    public String issuedToken(String tokenName, long expiryTime, String userIdx) {
        final Date now = new Date();

        final Claims claims = Jwts.claims()
                .setSubject(tokenName)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiryTime));

        claims.put(CLAIM_NAME, userIdx);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        final byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // JWT 토큰 검증
    public boolean verifyToken(String token) {
        try {
            final Claims claims = getBody(token);
            return true;
        } catch (RuntimeException e) {
            if (e instanceof ExpiredJwtException) {
                throw new BasicException(ErrorStatus.TOKEN_TIME_EXPIRED_EXCEPTION, ErrorStatus.TOKEN_TIME_EXPIRED_EXCEPTION.getMessage());
            }
            return false;
        }
    }


    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 토큰 내용 확인
    public String getJwtContents(String token) {
        final Claims claims = getBody(token);
        return (String) claims.get(CLAIM_NAME);
    }

}
