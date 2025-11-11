package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionReportDTO {
    private String sessionDate;
    private String studentId;
    private String sessionDuration;
    private String notes;
    private String status;
}
