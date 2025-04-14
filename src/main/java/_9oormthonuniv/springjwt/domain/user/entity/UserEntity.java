package _9oormthonuniv.springjwt.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY 설정해야 중복 값 없음
    private int id;

    private String username;

    private String password;

    private String role; // 권한

}
