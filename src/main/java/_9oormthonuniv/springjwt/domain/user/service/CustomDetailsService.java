package _9oormthonuniv.springjwt.domain.user.service;

import _9oormthonuniv.springjwt.domain.user.dto.CustomUserDetials;
import _9oormthonuniv.springjwt.domain.user.entity.UserEntity;
import _9oormthonuniv.springjwt.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 인자로 받은 username을 던져줘서 조회
        UserEntity userData = userRepository.findByUsername(username);

        // 조회받은 값이 null이 아니면 custom
        if (userData != null) {
            return new CustomUserDetials(userData);
        }
        return null;
    }
}
