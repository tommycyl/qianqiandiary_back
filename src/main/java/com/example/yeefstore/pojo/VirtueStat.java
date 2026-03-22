package com.example.yeefstore.pojo;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VirtueStat {
    private Integer type;
    private Integer count;
}
