package me.parkprin.careermanagementsystem.controller.menu;

import me.parkprin.careermanagementsystem.dto.response.ResponseDTO;
import me.parkprin.careermanagementsystem.dto.userandperson.UserAndPersonDTO;
import me.parkprin.careermanagementsystem.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu/api")
public class MenuController {

    private static final Long V_1 = new Long(1);

    @Autowired
    MenuService menuService;

    @GetMapping("/v1/{userId}")
    public ResponseDTO menu(@PathVariable String userId){

        UserAndPersonDTO userAndPersonDTO = UserAndPersonDTO.builder().
                userId(userId.equals("null") ? null : userId)
                .build();
        userAndPersonDTO.setVersion(V_1);
        try {
            return ResponseDTO.builder().state(200)
                    .responseType("UserAndPerson")
                    .responseValue(menuService.getMenuList(userAndPersonDTO)).build();
        } catch (Exception e){
            return ResponseDTO.builder().state(400)
                    .responseType("Exception Message")
                    .responseValue(e.getMessage()).build();
        }
    }
}
