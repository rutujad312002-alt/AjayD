package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDTO {
    private String certificateId;
    private String studentId;
    private String type;
    private String issueDate;
    private String expiryDate;
}
