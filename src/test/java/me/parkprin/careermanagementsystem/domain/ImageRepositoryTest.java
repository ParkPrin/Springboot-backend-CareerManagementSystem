package me.parkprin.careermanagementsystem.domain;

import me.parkprin.careermanagementsystem.domain.image.Image;
import me.parkprin.careermanagementsystem.domain.image.ImageRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageRepositoryTest {

    @Autowired
    ImageRepository imageRepository;

    @After
    public void cleanup() {
        imageRepository.deleteAll();
    }

    @Test
    public void 이미지저장_테스트() throws IOException {
        ClassPathResource pathResource = new ClassPathResource("image/profile.jpeg");
        byte[] imageFiles = Files.readAllBytes(pathResource.getFile().toPath());
        imageRepository.save(Image.builder().imageName(pathResource.getFilename()).imageType("resume").data(imageFiles).build());
        Image image = imageRepository.findAll().get(0);
        assertThat(image.getImageName()).isEqualTo(pathResource.getFilename());
    }
}
