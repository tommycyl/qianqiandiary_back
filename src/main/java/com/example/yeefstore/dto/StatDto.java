package com.example.yeefstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatDto {
    private int totalCount;
    private Long days;
    private List<Stat> stats;
}
