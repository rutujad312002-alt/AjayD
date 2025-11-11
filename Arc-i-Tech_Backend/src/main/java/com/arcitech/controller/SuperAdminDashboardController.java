package com.arcitech.controller;

import com.arcitech.dto.dashboard.SuperAdminDashboardDTO;
import com.arcitech.service.dashboard.SuperAdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard/super-admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
@RequiredArgsConstructor
public class SuperAdminDashboardController {
    private final SuperAdminDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<SuperAdminDashboardDTO> getDashboard(@RequestParam String userId) {
        return ResponseEntity.ok(dashboardService.getDashboardData(userId));
    }

    @PostMapping("/sub-admin")
    public ResponseEntity<Void> createSubAdmin(
            @RequestParam String email,
            @RequestParam String name) {
        dashboardService.createSubAdmin(email, name);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/sub-admin/{subAdminId}")
    public ResponseEntity<Void> deactivateSubAdmin(@PathVariable String subAdminId) {
        dashboardService.deactivateSubAdmin(subAdminId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sub-admin/{subAdminId}/permissions")
    public ResponseEntity<Void> setSubAdminPermissions(
            @PathVariable String subAdminId,
            @RequestBody List<String> permissions) {
        dashboardService.setSubAdminPermissions(subAdminId, permissions);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/service-category")
    public ResponseEntity<Void> createServiceCategory(@RequestParam String categoryName) {
        dashboardService.createServiceCategory(categoryName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getSystemAnalytics() {
        return ResponseEntity.ok(dashboardService.getSystemAnalytics());
    }

    @GetMapping("/audit-logs")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogs(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(dashboardService.getAuditLogs(startDate, endDate));
    }

    @PostMapping("/backup")
    public ResponseEntity<Void> initiateBackup() {
        dashboardService.initiateBackup();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/backup/status")
    public ResponseEntity<BackupStatusDTO> getBackupStatus() {
        return ResponseEntity.ok(dashboardService.getBackupStatus());
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDTO>> getSystemNotifications() {
        return ResponseEntity.ok(dashboardService.getSystemNotifications());
    }
}