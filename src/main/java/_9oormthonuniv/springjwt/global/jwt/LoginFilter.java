package _9oormthonuniv.springjwt.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    // 주입 받은거!~
    private AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
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
        System.out.println("로그인 성공");
    }

    @Override
    // 로그인 실패시 실행
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("로그인 실패");
    }


}


/*
    만든 필터를 등록해야함.
    SecurityConfig에서 만든 Filter를 등록해야 함.
 */
