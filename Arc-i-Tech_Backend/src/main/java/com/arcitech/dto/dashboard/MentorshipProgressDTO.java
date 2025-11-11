package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorshipProgressDTO {
    private String mentee;
    private String progressPercentage;
    private String currentModule;
    private String nextMilestone;
    private String lastReviewDate;
}
