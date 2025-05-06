package _9oormthonuniv.springjwt.global.jwt;

import _9oormthonuniv.springjwt.domain.user.dto.CustomUserDetails;
import _9oormthonuniv.springjwt.domain.user.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 요청에 대해 한 번만 동작하는 OncePerRequestFilter를 상속 받음
public class JWTFilter extends OncePerRequestFilter {

    /*
    JWTFilter는 이미 JWT로 인증 완료된 사용자를 위한 필터임.
    인증을 새로 하는 것이 아니라, 인증된 사용자를 SecurityContext에 넣는 작업

    Spring Security 필터 체인에서
    요청이 들어오면 여러 필터들이 차례대로 호출됨.
    그 중 JWTFilter는 OncePerRequestFilter라 한 번만 실행되는 필터임.

    JWT를 request에서 뽑아내고 검증 진행
    JWTUtil에서 필터를 검증할 메소드를 가져와야함.
     */
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 내부에 토큰을 검증하는 구현 과정 구현

        // request에서 Autorization 키값(헤더)를 뽑기
        String authorization = request.getHeader("Authorization");

        // 뽑아온 키 값에서 authorization에 토큰이 담겨있는지 검증 (null 인지 Bearer 달렸는지)
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("Authorization header is missing");
            filterChain.doFilter(request, response);    // return 전에 받은 request와 response를 다음 필터(doFilter)에 넘김

            // 조건 해당되면 return (필수)
            return;
        }

        System.out.println("Authorization now");

        // Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        // 토큰 소멸시간 검증
        if (jwtUtil.isExpired(token)) {

            System.out.println("JWT token is expired");
            filterChain.doFilter(request, response);

            // 조건 해당되면 return
            return;
        }

        // 토큰에서 username, role 값 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // userEntity 생성해서 값 set
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("temppassword"); // 임의 값
        userEntity.setRole(role);

        /*
        토큰을 기반으로 일시적인 세션을 만들고,
        SecurityContextHolder라는 시큐리티 세션에다 user를 일시적으로 저장하면
        특정 경로에 접근 가능
         */

        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        // 위에서 생성한 customUserDetails 객체를 UsernamePasswordAuthenticationToken을 만들고 authToken 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 유저 세션 생성
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
