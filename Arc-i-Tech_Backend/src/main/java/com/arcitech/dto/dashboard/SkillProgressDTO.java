package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillProgressDTO {
    private String skillName;
    private String proficiencyLevel;
    private String progressPercentage;
    private String lastAssessmentDate;
}
