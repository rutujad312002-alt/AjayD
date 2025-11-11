package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstallationDTO {
    private String version;
    private String installDate;
    private String status;
    private String systemRequirements;
}
