package me.parkprin.careermanagementsystem.service.resume;

import me.parkprin.careermanagementsystem.common.CommonUtils;
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
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ResumeService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    ResumeRepository resumeRepository;

    @Autowired
    CommonUtils commonUtils;

    public List<ResumeDTO> selectResumeByUserId(String userId) throws UnsupportedEncodingException {
        //// data:image/png;base64,
        User user = userRepository.selectByUserId(userId);
        return imageChangeResumeList(resumeRepository.selectByUserId(user.getId()));
    }

    public List<ResumeDTO> imageChangeResumeList(List<Resume> resumeList) throws UnsupportedEncodingException {

        List<ResumeDTO> resumeDTOList = new ArrayList<ResumeDTO>();
        Iterator<Resume> resumeIterator = resumeList.iterator();
        while(resumeIterator.hasNext()) {
            Resume resume = resumeIterator.next();
            Image image = resume.getImage();
            resumeDTOList.add(ResumeDTO.builder().
                    id(resume.getId()).
                    userId(resume.getUser().getUserId()).
                    imageId(image.getId()).
                    imageName(image.getImageName()).
                    imageType(image.getImageType()).
                    data(commonUtils.base64ImageByteArrayConvertString(image.getData(), image.getImageType())).
                    resumeName(resume.getResumeName()).
                    resumeSummary(resume.getResumeSummary()).
                    career(resume.getCareer()).
                    resumeSalary(resume.getResumeSalary()).build());
        }
        return resumeDTOList;
    }

    public ResumeDTO selectResumeDetail(Long resumeId) throws UnsupportedEncodingException {
        Resume resume = resumeRepository.findById(resumeId).get();
        Image image = resume.getImage();
        return ResumeDTO.builder().
                id(resume.getId()).
                userId(resume.getUser().getUserId()).
                imageId(image.getId()).
                imageName(image.getImageName()).
                imageType(image.getImageType()).
                data(commonUtils.base64ImageByteArrayConvertString(image.getData(), image.getImageType())).
                resumeName(resume.getResumeName()).
                resumeSummary(resume.getResumeSummary()).
                career(resume.getCareer()).
                resumeSalary(resume.getResumeSalary()).build();

    }

    public ResumeDTO copyResume(Long resumeId) throws UnsupportedEncodingException {
        Resume resume = resumeRepository.findById(resumeId).get();
        Resume copyResume = resumeRepository.save(Resume.builder().
                user(resume.getUser()).
                image(resume.getImage()).
                resumeName(resume.getResumeName()).
                resumeSalary(resume.getResumeSalary()).
                career(resume.getCareer()).
                resumeSummary(resume.getResumeSummary()).
                dateCreated(LocalDateTime.now()).
                lastUpdated(LocalDateTime.now())
                .build());
        Image image = copyResume.getImage();
        return ResumeDTO.builder().
                id(copyResume.getId()).
                userId(copyResume.getUser().getUserId()).
                imageId(copyResume.getImage().getId()).
                imageName(image.getImageName()).
                imageType(image.getImageType()).
                data(commonUtils.base64ImageByteArrayConvertString(image.getData(), image.getImageType())).
                resumeName(copyResume.getResumeName()).
                resumeSummary(copyResume.getResumeSummary()).
                career(copyResume.getCareer()).
                resumeSalary(copyResume.getResumeSalary()).build();

    }

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

    public Resume update(ResumeDTO resumeDTO, boolean isChangeImage) throws Exception {
        Image image = null;
        Resume resume = null;
        try {
            resume = resumeRepository.findById(resumeDTO.getId()).get();
            if (isChangeImage) {
                image = imageService.save(resumeDTO);
                resume.setImage(image);
            }
            resume.setResumeName(resumeDTO.getResumeName());
            resume.setResumeSalary(resumeDTO.getResumeSalary());
            resume.setResumeSummary(resumeDTO.getResumeSummary());
            resumeRepository.save(resume);
        } catch (Exception e){
            if (image != null) imageService.delete(image);
            throw new Exception("이력서 등록 중 오류가 발생하였습니다");
        }
        return resume;
    }

    public boolean removeResume(Long resumeId) throws Exception {
        boolean result = false;
        try {
            Resume resume = resumeRepository.findById(resumeId).get();
            resumeRepository.deleteById(resumeId);
            if (resumeRepository.selectByImageId(resume.getImage().getId()).size() == 0) imageService.delete(resume.getImage());
            result = true;
        } catch (Exception e) {
            throw new Exception();
        }
        return result;
    }


}
