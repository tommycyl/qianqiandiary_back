package com.example.yeefstore.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VirtueStatDto<T> {
    private Integer days;
    private Integer totalCount;
    private Integer maxDays;
    private List<T> stats;
}
