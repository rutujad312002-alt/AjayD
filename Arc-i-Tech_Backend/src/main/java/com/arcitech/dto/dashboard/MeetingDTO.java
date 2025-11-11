package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDTO {
    private String id;
    private String title;
    private String description;
    private String scheduledTime;
    private String attendees;
    private String location;
}
