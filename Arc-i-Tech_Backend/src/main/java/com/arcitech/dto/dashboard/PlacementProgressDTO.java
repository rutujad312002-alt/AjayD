package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlacementProgressDTO {
    private String studentId;
    private String placementStatus;
    private String appliedPositions;
    private String interviewScheduled;
    private String offerReceived;
}
