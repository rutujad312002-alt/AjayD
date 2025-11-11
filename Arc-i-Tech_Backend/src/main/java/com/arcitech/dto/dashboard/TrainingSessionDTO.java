package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingSessionDTO {
    private String sessionDate;
    private String topic;
    private String duration;
    private String instructor;
    private String materials;
}
