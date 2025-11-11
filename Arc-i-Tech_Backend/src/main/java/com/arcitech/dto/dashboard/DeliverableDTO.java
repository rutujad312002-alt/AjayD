package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliverableDTO {
    private String id;
    private String name;
    private String description;
    private String fileUrl;
    private String status;
    private String type;
}
