package _9oormthonuniv.springjwt.dto;

import lombok.Getter;
import lombok.Setter;

// 회원가입 데이터를 받을 DTO
@Getter
@Setter
public class JoinDTO {

    private String username;
    private String password;
}
