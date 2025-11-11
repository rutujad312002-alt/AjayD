package com.arcitech.dto.dashboard;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeamMemberDashboardDTO {
    private List<InterviewSession> upcomingInterviews;
    private List<PlacementGuidance> guidanceSessions;
    private List<StudentProgress> studentProgress;
    private TeamMemberStats stats;
}

@Data
@Builder
class InterviewSession {
    private Long sessionId;
    private String studentName;
    private String scheduledTime;
    private String interviewType;
    private String status;
}

@Data
@Builder
class PlacementGuidance {
    private Long sessionId;
    private String studentName;
    private String topic;
    private String scheduledTime;
}

@Data
@Builder
class StudentProgress {
    private Long studentId;
    private String studentName;
    private Integer mockTestsCompleted;
    private Integer interviewsCompleted;
    private Double averageScore;
}

@Data
@Builder
class TeamMemberStats {
    private Integer totalStudents;
    private Integer activeStudents;
    private Integer completedInterviews;
    private Integer scheduledInterviews;
    private Double averageStudentRating;
}