package com.example.yeefstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Oneday {
    private String user_id;
    private LocalDate create_time;
}
