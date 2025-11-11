package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDTO {
    private String id;
    private String testName;
    private Integer score;
    private Integer totalMarks;
    private Double percentage;
    private LocalDateTime takenAt;
    private String status;
}
