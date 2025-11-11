package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewDTO {
    private String studentId;
    private String interviewDate;
    private String interviewTime;
    private String interviewer;
    private String topicsCovered;
}
