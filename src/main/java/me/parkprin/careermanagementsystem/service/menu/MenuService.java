package me.parkprin.careermanagementsystem.service.menu;

import me.parkprin.careermanagementsystem.domain.menu.Menu;
import me.parkprin.careermanagementsystem.domain.menu.MenuRepository;
import me.parkprin.careermanagementsystem.domain.role.Role;
import me.parkprin.careermanagementsystem.domain.rolemappingmenu.RoleMappingMenu;
import me.parkprin.careermanagementsystem.domain.rolemappingmenu.RoleMappingMenuRepository;
import me.parkprin.careermanagementsystem.domain.rolemappinguser.RoleMappingUser;
import me.parkprin.careermanagementsystem.domain.rolemappinguser.RoleMappingUserRepository;
import me.parkprin.careermanagementsystem.domain.user.User;
import me.parkprin.careermanagementsystem.domain.user.UserRepository;
import me.parkprin.careermanagementsystem.dto.menu.MenuDTO;
import me.parkprin.careermanagementsystem.dto.userandperson.UserAndPersonDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleMappingUserRepository roleMappingUserRepository;

    @Autowired
    RoleMappingMenuRepository roleMappingMenuRepository;

    @Autowired
    MenuRepository menuRepository;

    public List<MenuDTO> getMenuList(UserAndPersonDTO userAndPersonDTO) throws Exception {
        if (StringUtils.isEmpty(userAndPersonDTO.getUserId())){
            List<Menu> menuList = menuRepository.selectByIsBasicMenu();
            return transMenuToMenuDTO(menuList);
        }
        else if (userRepository.selectByUserId(userAndPersonDTO.getUserId()) != null){
            List<Menu> menuList = new ArrayList<>();
            Map<String, String> menuIdMap = new HashMap<>();
            try {
                User user = userRepository.selectByUserId(userAndPersonDTO.getUserId());
                List<RoleMappingUser> roleMappingUserList = roleMappingUserRepository.selectByUserId(user.getId());
                for (RoleMappingUser roleMappingUser : roleMappingUserList){
                    Role role = roleMappingUser.getRole();
                    List<RoleMappingMenu> roleMappingMenuList = roleMappingMenuRepository.selectByRoleId(role.getId());
                    for (RoleMappingMenu roleMappingMenu : roleMappingMenuList){
                        Menu menu =  roleMappingMenu.getMenu();
                        if (!menuIdMap.containsKey(menu.getMenuId())){
                            menuIdMap.put(menu.getMenuId(), menu.getMenuId());
                            menuList.add(menu);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return transMenuToMenuDTO(menuList);
        } else {
            List<Menu> menuList = menuRepository.selectByIsBasicMenu();
            return transMenuToMenuDTO(menuList);
        }
    }

    private List<MenuDTO> transMenuToMenuDTO(List<Menu> menuList){
        List<MenuDTO> menuDTOList = new ArrayList<>();
        for (Menu menu : menuList){
            menuDTOList.add(MenuDTO.builder()
                    .name(menu.getMenuName())
                    .iconName(menu.getIconName())
                    .url(menu.getUrl()).build());
        }
        return menuDTOList;
    }
}
