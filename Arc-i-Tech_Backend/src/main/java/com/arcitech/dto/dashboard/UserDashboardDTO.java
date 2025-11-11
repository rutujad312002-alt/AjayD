package com.arcitech.dto.dashboard;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDashboardDTO {
    private List<ProjectProgress> projects;
    private List<TrainingProgress> training;
    private List<Certificate> certificates;
    private List<UpcomingSession> upcomingSessions;
    private UserStats stats;
}

@Data
@Builder
class ProjectProgress {
    private Long projectId;
    private String projectName;
    private String status;
    private Double progress;
    private String developerTeam;
}

@Data
@Builder
class TrainingProgress {
    private String trainingType;
    private Integer completedSessions;
    private Integer totalSessions;
    private Double progress;
    private String mentorName;
}

@Data
@Builder
class Certificate {
    private Long certificateId;
    private String name;
    private String issuedDate;
    private String issuedBy;
}

@Data
@Builder
class UpcomingSession {
    private Long sessionId;
    private String sessionType;
    private String scheduledTime;
    private String mentorName;
}

@Data
@Builder
class UserStats {
    private Integer completedProjects;
    private Integer ongoingProjects;
    private Integer earnedCertificates;
    private Integer completedMockTests;
    private Double averageTestScore;
}