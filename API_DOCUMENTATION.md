# API Documentation

Complete API reference for Arc-i-Tech Dashboard System.

## 📋 Table of Contents

- [Overview](#overview)
- [Authentication](#authentication)
- [Base URL](#base-url)
- [Response Format](#response-format)
- [Error Handling](#error-handling)
- [Endpoints](#endpoints)

## 📖 Overview

The Arc-i-Tech API provides REST endpoints for dashboard functionality, user management, project tracking, and team collaboration. All endpoints require JWT authentication except for login and registration.

**API Version**: v1
**Status**: Production Ready

## 🔐 Authentication

### JWT Token

All authenticated endpoints require a Bearer token in the `Authorization` header:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Token Expiration

- **Lifetime**: 24 hours
- **Refresh**: Obtain new token by logging in
- **Error Response**: 401 Unauthorized

## 🌐 Base URL

```
http://localhost:8080/api/v1
```

### Production URLs

```
https://api.arcitech.com/api/v1
```

## 📤 Response Format

### Success Response (200 OK)

```json
{
  "status": "success",
  "code": 200,
  "message": "Operation successful",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com"
  },
  "timestamp": "2025-11-11T10:30:00Z"
}
```

### Error Response (4xx, 5xx)

```json
{
  "status": "error",
  "code": 400,
  "message": "Invalid request parameters",
  "error": "Validation failed",
  "timestamp": "2025-11-11T10:30:00Z"
}
```

## ❌ Error Handling

### HTTP Status Codes

| Code | Meaning | Description |
|------|---------|-------------|
| 200 | OK | Request successful |
| 201 | Created | Resource created successfully |
| 204 | No Content | Successful but no content to return |
| 400 | Bad Request | Invalid parameters or format |
| 401 | Unauthorized | Missing or invalid authentication |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource not found |
| 409 | Conflict | Resource already exists |
| 500 | Server Error | Internal server error |

### Common Error Responses

**Missing Authentication**:
```json
{
  "status": "error",
  "code": 401,
  "message": "Unauthorized",
  "error": "Missing or expired JWT token"
}
```

**Insufficient Permissions**:
```json
{
  "status": "error",
  "code": 403,
  "message": "Access Denied",
  "error": "User role does not have permission for this operation"
}
```

**Invalid Input**:
```json
{
  "status": "error",
  "code": 400,
  "message": "Validation failed",
  "error": "Email field is required"
}
```

---

## 🔌 Endpoints

### Authentication Endpoints

#### 1. User Login

Register a new user account.

```http
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
      "id": 1,
      "name": "John Doe",
      "email": "user@example.com",
      "role": "PROJECT_DEVELOPER_TEAM"
    }
  }
}
```

**Error Response (401 Unauthorized)**:
```json
{
  "status": "error",
  "code": 401,
  "message": "Invalid credentials"
}
```

#### 2. User Registration

Register a new user account.

```http
POST /auth/register
Content-Type: application/json

{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "password": "securePassword123!",
  "role": "SOFTWARE_USER"
}
```

**Response (201 Created)**:
```json
{
  "status": "success",
  "code": 201,
  "message": "User registered successfully",
  "data": {
    "id": 2,
    "name": "Jane Doe",
    "email": "jane@example.com",
    "role": "SOFTWARE_USER"
  }
}
```

---

### Dashboard Endpoints

All dashboard endpoints require authentication.

#### 1. Super Admin Dashboard

Get comprehensive system analytics and administration data.

```http
GET /dashboard/super-admin?userId=1
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": {
    "totalUsers": 150,
    "totalProjects": 45,
    "activeTeams": 12,
    "systemHealth": 98.5,
    "recentActivity": [],
    "auditLogs": []
  }
}
```

**Required Role**: `SUPER_ADMIN`

#### 2. Sub Admin Dashboard

Get team and project management data.

```http
GET /dashboard/sub-admin?userId=2
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": {
    "teamCount": 5,
    "memberCount": 30,
    "projectCount": 15,
    "approvalsPending": 3,
    "teams": [],
    "projects": []
  }
}
```

**Required Role**: `SUB_ADMIN`

#### 3. Project Developer Dashboard

Get project and deployment tracking data.

```http
GET /dashboard/project-developer?userId=3
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": {
    "assignedProjects": 8,
    "completedTasks": 45,
    "deployments": 12,
    "teamSize": 5,
    "projects": [],
    "tasks": []
  }
}
```

**Required Role**: `PROJECT_DEVELOPER_TEAM`

#### 4. Team Member Dashboard

Get training and student management data.

```http
GET /dashboard/team-member?userId=4
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": {
    "studentCount": 20,
    "completedSessions": 35,
    "certificatesIssued": 8,
    "upcomingSessions": 3,
    "students": [],
    "sessions": []
  }
}
```

**Required Role**: `TEAM_MEMBER`

#### 5. User Dashboard

Get personal project and training information.

```http
GET /dashboard/user?userId=5
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": {
    "enrolledProjects": 4,
    "completedTraining": 8,
    "currentSkills": 15,
    "certificates": [],
    "progressUpdates": []
  }
}
```

**Required Roles**: `SOFTWARE_USER`, `TRAINING_USER`

---

### Team Management Endpoints

#### 1. Create Team (Sub Admin Only)

```http
POST /teams
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Backend Team",
  "teamType": "DEVELOPMENT",
  "description": "Team for backend development"
}
```

**Response (201 Created)**:
```json
{
  "status": "success",
  "code": 201,
  "data": {
    "id": 1,
    "name": "Backend Team",
    "teamType": "DEVELOPMENT",
    "createdAt": "2025-11-11T10:30:00Z"
  }
}
```

#### 2. Get Teams

```http
GET /teams?limit=10&offset=0
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": [
    {
      "id": 1,
      "name": "Backend Team",
      "memberCount": 5,
      "projectCount": 3
    }
  ]
}
```

#### 3. Add Team Member (Sub Admin Only)

```http
POST /teams/1/members
Authorization: Bearer {token}
Content-Type: application/json

{
  "userId": 10,
  "role": "DEVELOPER"
}
```

**Response (201 Created)**:
```json
{
  "status": "success",
  "code": 201,
  "message": "Member added successfully"
}
```

#### 4. Remove Team Member (Sub Admin Only)

```http
DELETE /teams/1/members/10
Authorization: Bearer {token}
```

**Response (204 No Content)**

---

### Project Management Endpoints

#### 1. Get Projects

```http
GET /projects?status=active&limit=20&offset=0
Authorization: Bearer {token}
```

**Query Parameters**:
- `status`: `active`, `completed`, `onhold` (optional)
- `limit`: Number of results (default: 20)
- `offset`: Pagination offset (default: 0)

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": {
    "total": 45,
    "projects": [
      {
        "id": 1,
        "title": "E-Commerce Platform",
        "status": "active",
        "progress": 65,
        "teamId": 2,
        "startDate": "2025-01-01",
        "endDate": "2025-12-31"
      }
    ]
  }
}
```

#### 2. Create Project

```http
POST /projects
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Mobile App",
  "description": "iOS and Android app",
  "teamId": 1,
  "startDate": "2025-12-01",
  "endDate": "2026-06-30"
}
```

**Response (201 Created)**:
```json
{
  "status": "success",
  "code": 201,
  "data": {
    "id": 46,
    "title": "Mobile App",
    "progress": 0,
    "status": "pending"
  }
}
```

#### 3. Update Project Progress

```http
PUT /projects/1/progress
Authorization: Bearer {token}
Content-Type: application/json

{
  "progress": 75,
  "notes": "On track for completion"
}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "message": "Progress updated successfully"
}
```

#### 4. Get Project Details

```http
GET /projects/1
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": {
    "id": 1,
    "title": "E-Commerce Platform",
    "description": "Full-stack e-commerce solution",
    "status": "active",
    "progress": 65,
    "teamId": 2,
    "tasks": [],
    "timeline": {},
    "deliverables": []
  }
}
```

---

### Training & Certification Endpoints

#### 1. Submit Training Session

```http
POST /training/sessions
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Java Advanced Concepts",
  "description": "Advanced Java programming",
  "scheduleDate": "2025-11-20T10:00:00Z",
  "duration": 120
}
```

**Response (201 Created)**:
```json
{
  "status": "success",
  "code": 201,
  "data": {
    "id": 1,
    "title": "Java Advanced Concepts",
    "status": "scheduled"
  }
}
```

#### 2. Get Training Sessions

```http
GET /training/sessions?userId=4&status=completed
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": [
    {
      "id": 1,
      "title": "Java Advanced Concepts",
      "status": "completed",
      "completedDate": "2025-11-20"
    }
  ]
}
```

#### 3. Issue Certificate

```http
POST /certificates
Authorization: Bearer {token}
Content-Type: application/json

{
  "userId": 5,
  "title": "Advanced Java Developer",
  "issuedDate": "2025-11-20"
}
```

**Response (201 Created)**:
```json
{
  "status": "success",
  "code": 201,
  "data": {
    "id": 1,
    "userId": 5,
    "title": "Advanced Java Developer",
    "issuedDate": "2025-11-20",
    "approved": true
  }
}
```

---

### Approval & Rejection Endpoints

#### 1. Approve Update (Sub Admin)

```http
POST /approvals/project-updates/1/approve
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "message": "Update approved successfully"
}
```

#### 2. Reject Update (Sub Admin)

```http
POST /approvals/project-updates/1/reject
Authorization: Bearer {token}
Content-Type: application/json

{
  "reason": "Missing documentation"
}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "message": "Update rejected successfully"
}
```

#### 3. Approve Certificate

```http
POST /approvals/certificates/1/approve
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "message": "Certificate approved"
}
```

---

### Analytics & Reporting Endpoints

#### 1. Get System Analytics (Super Admin)

```http
GET /analytics/system
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": {
    "totalUsers": 150,
    "activeUsers": 98,
    "totalProjects": 45,
    "completedProjects": 12,
    "totalTeams": 8,
    "systemHealthScore": 98.5
  }
}
```

#### 2. Get Project Reports

```http
GET /reports/projects?startDate=2025-01-01&endDate=2025-11-30
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": {
    "totalProjects": 45,
    "completedProjects": 12,
    "averageCompletion": 68.5,
    "teamPerformance": []
  }
}
```

#### 3. Get Training Reports

```http
GET /reports/training?year=2025
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": {
    "totalSessions": 125,
    "completedSessions": 98,
    "certificatesIssued": 45,
    "skillsImproved": 250
  }
}
```

---

### Audit & Logging Endpoints

#### 1. Get Audit Logs (Super Admin)

```http
GET /audit-logs?limit=50&offset=0
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "status": "success",
  "code": 200,
  "data": [
    {
      "id": 1,
      "action": "CREATE_USER",
      "userId": 1,
      "timestamp": "2025-11-11T10:30:00Z",
      "details": "User created successfully"
    }
  ]
}
```

---

## 🔑 Role-Based Access Control

### Permission Matrix

| Endpoint | SUPER_ADMIN | SUB_ADMIN | PROJECT_DEV | TEAM_MEMBER | USER |
|----------|-------------|-----------|-------------|-------------|------|
| `/dashboard/super-admin` | ✅ | ❌ | ❌ | ❌ | ❌ |
| `/dashboard/sub-admin` | ❌ | ✅ | ❌ | ❌ | ❌ |
| `/dashboard/project-developer` | ❌ | ❌ | ✅ | ❌ | ❌ |
| `/dashboard/team-member` | ❌ | ❌ | ❌ | ✅ | ❌ |
| `/dashboard/user` | ❌ | ❌ | ❌ | ❌ | ✅ |
| `/teams` | ✅ | ✅ | ❌ | ❌ | ❌ |
| `/projects` | ✅ | ✅ | ✅ | ❌ | ✅ |
| `/training/sessions` | ✅ | ✅ | ✅ | ✅ | ✅ |
| `/approvals/*` | ✅ | ✅ | ❌ | ❌ | ❌ |

---

## 🧪 Testing with Postman

### Import Collection

1. Download `Arc-i-Tech.postman_collection.json`
2. Open Postman
3. Click "Import" → Select file
4. Environment automatically configured

### Testing Workflow

1. **Login**: Execute login request to get token
2. **Copy Token**: Copy JWT from response
3. **Set Bearer Token**: In Authorization tab
4. **Test Endpoints**: Execute dashboard requests

---

## 📞 API Support

For API issues:
- Check error response messages
- Verify authentication token
- Confirm user role permissions
- Review request format
- Check server logs

---

**Last Updated**: November 11, 2025
**API Version**: 1.0.0
**Status**: Production Ready
