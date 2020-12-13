package me.parkprin.careermanagementsystem.service.image;

import me.parkprin.careermanagementsystem.domain.image.Image;
import me.parkprin.careermanagementsystem.domain.image.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public Image save(MultipartFile file, String imageType) throws IOException {
        return imageRepository.save(Image.builder()
                .imageName(file.getOriginalFilename())
                .imageType(imageType).data(file.getBytes()).build());
    }
}
