package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultingSessionDTO {
    private String sessionDate;
    private String duration;
    private String topic;
    private String notes;
    private String consultant;
}
