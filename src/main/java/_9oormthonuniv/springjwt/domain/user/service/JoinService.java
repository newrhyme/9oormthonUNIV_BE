package _9oormthonuniv.springjwt.domain.user.service;

import _9oormthonuniv.springjwt.domain.user.dto.JoinDTO;
import _9oormthonuniv.springjwt.domain.user.entity.UserEntity;
import _9oormthonuniv.springjwt.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        // isExist : 리포지토리에 존재하는지 bool형 변수
        Boolean isExist = userRepository.existsByUsername(username);

        // True -> return 할 수 있도록
        if (isExist) {
            return;
        }

        // False -> 회원가입 진행해도 됨
        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        // 설정 마치면 리포지토리에 엔티티 저장하는 save 메소드
        userRepository.save(data);

    }
}
