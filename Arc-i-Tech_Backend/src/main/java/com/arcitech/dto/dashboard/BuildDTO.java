package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildDTO {
    private String version;
    private String platform;
    private String buildDate;
    private String status;
    private String downloadUrl;
}
