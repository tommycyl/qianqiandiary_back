package com.example.yeefstore.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WxLoginDto {
    private String code;
    private String openid;
    private String session_key;
    private Integer errcode;
    private String errmsg;
}
