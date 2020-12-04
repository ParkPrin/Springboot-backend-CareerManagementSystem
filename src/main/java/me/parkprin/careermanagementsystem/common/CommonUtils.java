package me.parkprin.careermanagementsystem.common;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    public boolean isNumberic(String strNum){
        if (strNum == null)
            return false;
        try {
            long l = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
