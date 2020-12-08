package me.parkprin.careermanagementsystem.dto.menu;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MenuDTO {

    private String name;
    private String iconName;
    private String url;


    @Builder
    public MenuDTO(String name, String iconName, String url){
        this.name = name;
        this.iconName = iconName;
        this.url = url;
    }
}
