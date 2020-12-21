package me.parkprin.careermanagementsystem.service.image;

import me.parkprin.careermanagementsystem.common.CommonUtils;
import me.parkprin.careermanagementsystem.domain.image.Image;
import me.parkprin.careermanagementsystem.domain.image.ImageRepository;
import me.parkprin.careermanagementsystem.dto.resume.ResumeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CommonUtils commonUtils;

    public Image save(MultipartFile file, String imageType) throws IOException {
        return imageRepository.save(Image.builder()
                .imageName(file.getOriginalFilename())
                .imageType(imageType).data(file.getBytes()).build());
    }

    public Image save(ResumeDTO resumeDTO) throws IOException {
        return imageRepository.save(Image.builder()
                .imageName(resumeDTO.getImageName())
                .imageType(resumeDTO.getImageType())
                .data(commonUtils.base64ImageStringConvertByteArray(resumeDTO.getData()))
                .build());
    }

    public Image getImage(Long imageId) {
        return imageRepository.findById(imageId).get();
    }

    public void delete(Image image){
        imageRepository.delete(image);
    }
}
