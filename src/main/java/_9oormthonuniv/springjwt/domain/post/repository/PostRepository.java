package _9oormthonuniv.springjwt.domain.post.repository;

import _9oormthonuniv.springjwt.domain.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
