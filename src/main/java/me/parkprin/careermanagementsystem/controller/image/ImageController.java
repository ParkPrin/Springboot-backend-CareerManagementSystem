package me.parkprin.careermanagementsystem.controller.image;

import me.parkprin.careermanagementsystem.common.CommonUtils;
import me.parkprin.careermanagementsystem.dto.response.ResponseDTO;
import me.parkprin.careermanagementsystem.dto.userandperson.UserAndPersonDTO;
import me.parkprin.careermanagementsystem.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@RestController
@RequestMapping("/image/api")
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    CommonUtils commonUtils;

    @PostMapping("/v1/{imageType}")
    public ResponseDTO insertImage(@PathVariable String imageType,
                                   @RequestParam("file")MultipartFile file){
        try {
            return ResponseDTO.builder().state(200)
                    .responseType("image")
                    .responseValue(imageService.save(file, imageType)).build();
        } catch (Exception e){
            return ResponseDTO.builder().state(400)
                    .responseType("Exception Message")
                    .responseValue(e.getMessage()).build();
        }
    }

    @GetMapping("/v1/{imageId}")
    public String getImage(@PathVariable Long imageId) throws UnsupportedEncodingException {
        String base64 = commonUtils.base64ImageByteArrayConvertString(imageService.getImage(imageId).getData(), imageService.getImage(imageId).getImageType());
        Base64.getDecoder().decode(base64);

        /*
        byte[] image =Base64.getEncoder().encode(imageService.getImage(imageId).getData());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);
        return new HttpEntity<byte[]>(image, headers);*/
        return commonUtils.base64ImageByteArrayConvertString(imageService.getImage(imageId).getData(), imageService.getImage(imageId).getImageType());
    }
}
