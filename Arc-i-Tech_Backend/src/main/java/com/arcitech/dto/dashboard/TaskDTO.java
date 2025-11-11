package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private String id;
    private String title;
    private String description;
    private String status;
    private String assignedTo;
    private String dueDate;
    private String priority;
}
