package _9oormthonuniv.springjwt.repository;

import _9oormthonuniv.springjwt.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
