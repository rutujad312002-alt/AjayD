package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceDTO {
    private String studentId;
    private String rating;
    private String skillsAcquired;
    private String areasForImprovement;
    private String reviewDate;
}
