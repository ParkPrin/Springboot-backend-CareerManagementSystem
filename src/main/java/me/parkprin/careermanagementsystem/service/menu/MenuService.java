package me.parkprin.careermanagementsystem.service.menu;

import me.parkprin.careermanagementsystem.domain.menu.Menu;
import me.parkprin.careermanagementsystem.domain.menu.MenuRepository;
import me.parkprin.careermanagementsystem.dto.menu.MenuDTO;
import me.parkprin.careermanagementsystem.dto.userandperson.UserAndPersonDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;

    public List<MenuDTO> getMenuList(UserAndPersonDTO userAndPersonDTO){
        if (StringUtils.isEmpty(userAndPersonDTO.getUserId())){
            List<Menu> menuList = menuRepository.selectByIsBasicMenu();
            List<MenuDTO> menuDTOList = new ArrayList<>();
            for (Menu menu : menuList){
                menuDTOList.add(MenuDTO.builder()
                        .name(menu.getMenuName())
                        .iconName(menu.getIconName())
                        .url(menu.getUrl()).build());
            }
            return menuDTOList;
        }
        else {
            return null;
        }
    }
}
