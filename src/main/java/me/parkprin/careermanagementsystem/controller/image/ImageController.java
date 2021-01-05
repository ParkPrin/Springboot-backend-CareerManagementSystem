package me.parkprin.careermanagementsystem.controller.image;

import me.parkprin.careermanagementsystem.common.CommonUtils;
import me.parkprin.careermanagementsystem.dto.response.ResponseDTO;
import me.parkprin.careermanagementsystem.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping(value = "/v1/{imageId}")
    public @ResponseBody byte[] getImage(@PathVariable Long imageId) throws IOException {
        String base64 = commonUtils.base64ImageByteArrayConvertString(imageService.getImage(imageId).getData(), imageService.getImage(imageId).getImageType());
        return Base64.getDecoder().decode(Base64.getEncoder().encode(base64.getBytes()));
    }
}
