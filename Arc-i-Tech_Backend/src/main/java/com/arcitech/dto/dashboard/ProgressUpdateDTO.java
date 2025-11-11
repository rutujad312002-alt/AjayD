package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgressUpdateDTO {
    private String id;
    private String title;
    private String description;
    private Double percentage;
    private LocalDateTime updatedAt;
    private String status;
}
