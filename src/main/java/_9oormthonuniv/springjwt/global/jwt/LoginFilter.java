package _9oormthonuniv.springjwt.global.jwt;

import _9oormthonuniv.springjwt.domain.user.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    // AuthenticationManager 주입
    private final AuthenticationManager authenticationManager;

    // JWTUtil 주입
    private final JWTUtil jwtUtil;

    // 생성자 방식으로 주입
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    // 내부의 요청을 가로채서 담겨있는 usernamer과 p/w 값을 String으로 받음
    public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // request의 usernamer, p/w를 받아서 인증 진행 할 거임
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println(username);

        // username, p/w를 DTO UsernamePasswordAuthenticationToken에 담아서 AuthenticationManager에 담고,
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        // 토큰에 담은걸 AuthenticationManager에 던짐
        return authenticationManager.authenticate(authToken);
    }

    @Override
    // 로그인 성공시 실행
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {

        // user 객체를 알기 위해 customuserDetails 만들고 특정 user 확인
        CustomUserDetails customuserDetails = (CustomUserDetails) authentication.getPrincipal();

        // username 뽑기
        String username = customuserDetails.getUsername();

        /*
        role 값 뽑기
        Collection에서 authority 객체를 뽑고,
        Iterator를 통해서 반복 후 내부 객체 뽑아냄
         */
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        // 토큰을 만듦 (위에서 만든 username, role, 만료시간)
        String token = jwtUtil.createJwt(username, role, 60 * 60 * 10L);

        // JWT를 만들어서 Response에 넣기 (Header에 넣어서 응답)
        /*
        HTTP 인증 방식 (RFC 7235)
        Authorization : 타입 인증 토큰
        Authorization : Bearer 인증토큰string
         */
        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    // 로그인 실패시 실행
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        // 실패 -> 401
        response.setStatus(401);
    }


}


/*
    만든 필터를 등록해야함.
    SecurityConfig에서 만든 Filter를 등록해야 함.
 */
