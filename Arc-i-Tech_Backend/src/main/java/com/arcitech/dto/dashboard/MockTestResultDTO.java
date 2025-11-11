package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MockTestResultDTO {
    private String testId;
    private String studentId;
    private String testDate;
    private String score;
    private String totalMarks;
    private String percentageScore;
}
