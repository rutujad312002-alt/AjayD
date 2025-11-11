package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingReportDTO {
    private String trainingId;
    private String trainingName;
    private String instructor;
    private String enrollmentCount;
    private String completionRate;
    private String averageRating;
}
