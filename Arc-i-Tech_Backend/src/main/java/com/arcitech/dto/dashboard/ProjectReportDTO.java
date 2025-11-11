package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectReportDTO {
    private String projectId;
    private String projectName;
    private String status;
    private String completionPercentage;
    private String startDate;
    private String expectedEndDate;
}
