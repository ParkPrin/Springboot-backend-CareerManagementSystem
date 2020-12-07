package me.parkprin.careermanagementsystem.controller.userandperson;

import me.parkprin.careermanagementsystem.common.CommonUtils;
import me.parkprin.careermanagementsystem.dto.response.ResponseDTO;
import me.parkprin.careermanagementsystem.dto.userandperson.UserAndPersonDTO;
import me.parkprin.careermanagementsystem.service.UserAndPersonService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/api")
public class UserAndPersonController {

    private static final Long V_1 = new Long(1);

    @Autowired
    UserAndPersonService userAndPersonService;

    @Autowired
    CommonUtils commonUtils;

    @PostMapping("/join/v1")
    public ResponseDTO join(@RequestBody UserAndPersonDTO userAndPersonDTO){
        userAndPersonDTO.setVersion(V_1);
        try {
            return ResponseDTO.builder().state(200)
                    .responseType("UserAndPerson")
                    .responseValue(userAndPersonService.join(userAndPersonDTO)).build();
        } catch (Exception e){
            return ResponseDTO.builder().state(400)
                    .responseType("Exception Message")
                    .responseValue(e.getMessage()).build();
        }
    }

    @PostMapping("/login/v1")
    public ResponseDTO login(@RequestBody UserAndPersonDTO userAndPersonDTO){
        userAndPersonDTO.setVersion(V_1);
        try {
            return ResponseDTO.builder().state(200)
                    .responseType("loginCode")
                    .responseValue(userAndPersonService.login(
                            userAndPersonDTO.getUserId(), userAndPersonDTO.getPassword()))
                    .build();
        } catch (Exception e){
            return ResponseDTO.builder().state(400)
                    .responseType("Exception Message")
                    .responseValue(e.getMessage()).build();
        }
    }
}
