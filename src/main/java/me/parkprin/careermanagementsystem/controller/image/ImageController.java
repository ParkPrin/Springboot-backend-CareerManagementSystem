package me.parkprin.careermanagementsystem.controller.image;

import me.parkprin.careermanagementsystem.dto.response.ResponseDTO;
import me.parkprin.careermanagementsystem.dto.userandperson.UserAndPersonDTO;
import me.parkprin.careermanagementsystem.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image/api")
public class ImageController {

    @Autowired
    ImageService imageService;

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
}
