package me.parkprin.careermanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseDTO {
    private int state;
    private String responseType;
    private Object responseValue;

    @Builder
    public ResponseDTO(int state, String responseType, Object responseValue){
        this.state = state;
        this.responseType = responseType;
        this.responseValue = responseValue;
    }
}
