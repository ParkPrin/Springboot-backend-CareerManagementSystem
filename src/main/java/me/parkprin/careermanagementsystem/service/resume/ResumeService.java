package me.parkprin.careermanagementsystem.service.resume;

import me.parkprin.careermanagementsystem.domain.image.Image;
import me.parkprin.careermanagementsystem.domain.resume.Resume;
import me.parkprin.careermanagementsystem.domain.resume.ResumeRepository;
import me.parkprin.careermanagementsystem.domain.user.User;
import me.parkprin.careermanagementsystem.domain.user.UserRepository;
import me.parkprin.careermanagementsystem.dto.resume.ResumeDTO;
import me.parkprin.careermanagementsystem.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    ResumeRepository resumeRepository;

    public Resume save(ResumeDTO resumeDTO) throws Exception {
        User user = userRepository.selectByUserId(resumeDTO.getUserId());
        Resume resume = null;
        Image image = null;
        try {
            image = imageService.save(resumeDTO);
            resume = resumeRepository.save(Resume.builder().
                    user(user).
                    image(image).
                    resumeName(resumeDTO.getResumeName()).
                    resumeSalary(resumeDTO.getResumeSalary()).
                    career(resumeDTO.getCareer()).
                    resumeSummary(resumeDTO.getResumeSummary()).
                    dateCreated(LocalDateTime.now()).
                    lastUpdated(LocalDateTime.now())
                    .build());
        } catch (Exception e){
            if (image != null) imageService.delete(image);
            throw new Exception("이력서 등록 중 오류가 발생하였습니다");
        }
        return resume;
    }

    public List<Resume> selectResumeByUserId(String userId) {
        User user = userRepository.selectByUserId(userId);
        return resumeRepository.selectByUserId(user.getId());
    }
}
