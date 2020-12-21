package me.parkprin.careermanagementsystem.common;

import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

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

    public byte [] base64ImageStringConvertByteArray(String base64ImageString){
        return DatatypeConverter.parseBase64Binary(base64ImageString.split(",")[1]);
    }

    public String base64ImageByteArrayConvertString(byte[] base64BypeArray, String ImageType) throws UnsupportedEncodingException {
        // data:image/png;base64,
        return "data:" + ImageType + ";base64," + Base64.getEncoder().encodeToString(base64BypeArray);
    }
}
