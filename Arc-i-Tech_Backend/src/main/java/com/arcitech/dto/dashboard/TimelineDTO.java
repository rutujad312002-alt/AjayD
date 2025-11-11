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
public class TimelineDTO {
    private String id;
    private String event;
    private String description;
    private LocalDateTime date;
    private String status;
}
