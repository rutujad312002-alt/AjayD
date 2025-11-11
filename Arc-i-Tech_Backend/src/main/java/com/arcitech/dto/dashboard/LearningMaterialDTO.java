package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LearningMaterialDTO {
    private String id;
    private String title;
    private String description;
    private String fileUrl;
    private String type;
    private String instructor;
}
