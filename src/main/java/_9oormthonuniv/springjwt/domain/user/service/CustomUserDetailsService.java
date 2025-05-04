package _9oormthonuniv.springjwt.domain.user.service;

import _9oormthonuniv.springjwt.domain.user.dto.CustomUserDetails;
import _9oormthonuniv.springjwt.domain.user.entity.UserEntity;
import _9oormthonuniv.springjwt.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 DB에서 특정 User 조회하고 return하면됨
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // DB에 접근할 repository를 연결하기 위해서 userRepository 객체 선언
    private final UserRepository userRepository;

    // 생성자 방식으로
    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 인자로 받은 username을 던져줘서 조회
        UserEntity userData = userRepository.findByUsername(username);

        // 조회받은 username 검증
        if (userData != null) {
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
