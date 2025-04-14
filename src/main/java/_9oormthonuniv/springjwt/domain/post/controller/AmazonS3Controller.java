package _9oormthonuniv.springjwt.domain.post.controller;

import _9oormthonuniv.springjwt.domain.post.service.AwsS3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
public class AmazonS3Controller {

    private final AwsS3Service awsS3Service;

    public AmazonS3Controller(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @PostMapping("/multi")
    public ResponseEntity <List<String>> uploadFile(@RequestParam List<MultipartFile> multipartFiles) {
        return ResponseEntity.ok(awsS3Service.uploadFile(multipartFiles));
    }

    // 개별 이미지 업로드
    @PostMapping("/single")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile multipartFile) {
        return ResponseEntity.ok((awsS3Service.uploadFile(multipartFile)));
    }


    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteFile(fileName);
        return ResponseEntity.ok(fileName);
    }
}
