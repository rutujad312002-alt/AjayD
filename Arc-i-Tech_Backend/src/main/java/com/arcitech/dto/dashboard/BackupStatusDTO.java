package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BackupStatusDTO {
    private String status;
    private String lastBackupTime;
    private Long backupSize;
    private String backupLocation;
}
