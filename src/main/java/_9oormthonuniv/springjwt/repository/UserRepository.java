package _9oormthonuniv.springjwt.repository;


import _9oormthonuniv.springjwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String username);

    // username을 받아서 DB 테이블에서 회원을 조회하는 메소드
    UserEntity findByUsername(String username);
}
