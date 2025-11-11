package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDTO {
    private String id;
    private String requestType;
    private String status;
    private String submittedBy;
    private String submittedDate;
    private String description;
}
