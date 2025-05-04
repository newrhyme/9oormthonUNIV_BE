package _9oormthonuniv.springjwt.global.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// 컴포넌트로 등록해서 관리 (0.12.3)
@Component
public class JWTUtil {

    // 객체 키를 저장할 private 접근지정자의 secretKey 객체 변수
    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {

        // 키는 JWT에서 객체 타입으로 만들어서 저장해야함
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /*
    검증을 진행할 메소드들(getUsername, getRole, isExpired),
    토큰을 전달받아 내부에 Jwt parser를 이용해서 내부의 데이터 확인
     */

    // username 추출
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    // role 추출
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // token 만료되었는지 확인
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    /*
    로그인이 완료되었을 때, successfulHandler를 통해서
    username, role, 만료시간을 전달받아
    토큰 생성 후 응답하는 토큰 생성 메소드
     */
    public String createJwt(String username, String role, Long expiredMs) {

        // builder를 통해서 토큰 생성
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))                  // 현재 발행시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs))    // 만료 시간
                .signWith(secretKey)                                             // secretKey를 통해서 signature를 만듦 -> 암호화 진행
                .compact();                                                      // 토큰 compact
    }
}
