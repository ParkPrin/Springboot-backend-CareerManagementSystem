package me.parkprin.careermanagementsystem.controller.resume;

import me.parkprin.careermanagementsystem.dto.response.ResponseDTO;
import me.parkprin.careermanagementsystem.dto.resume.ResumeDTO;
import me.parkprin.careermanagementsystem.dto.userandperson.UserAndPersonDTO;
import me.parkprin.careermanagementsystem.service.resume.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/resume/api")
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    @PostMapping("/v1")
    public ResponseDTO saveResume(@RequestBody ResumeDTO resumeDTO){
        try {
            return ResponseDTO.builder().state(200)
                    .responseType("resume")
                    .responseValue(resumeService.save(resumeDTO)).build();
        } catch (Exception e){
            return ResponseDTO.builder().state(400)
                    .responseType("Exception Message")
                    .responseValue(e.getMessage()).build();
        }
    }

    @GetMapping("/v1/{userId}")
    public ResponseDTO selectResumeByUserId(@PathVariable String userId){
        try {
            return ResponseDTO.builder().state(200)
                    .responseType("resume")
                    .responseValue(resumeService.selectResumeByUserId(userId)).build();
        } catch (Exception e){
            return ResponseDTO.builder().state(400)
                    .responseType("Exception Message")
                    .responseValue(e.getMessage()).build();
        }
    }
}
