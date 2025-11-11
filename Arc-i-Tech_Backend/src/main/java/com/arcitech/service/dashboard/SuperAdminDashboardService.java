package com.arcitech.service.dashboard;

import com.arcitech.dto.dashboard.*;
import java.util.List;
import java.util.Map;

public interface SuperAdminDashboardService extends DashboardService<SuperAdminDashboardDTO> {
    SuperAdminDashboardDTO getSuperAdminDashboard();
    void createSubAdmin(String email, String name);
    void deactivateSubAdmin(String subAdminId);
    void setSubAdminPermissions(String subAdminId, List<String> permissions);
    void createServiceCategory(String categoryName);
    Map<String, Object> getSystemAnalytics();
    List<AuditLogDTO> getAuditLogs(String startDate, String endDate);
    void initiateBackup();
    BackupStatusDTO getBackupStatus();
    List<NotificationDTO> getSystemNotifications();
}