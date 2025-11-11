# Arc-i-Tech Platform API Contract

## Base URL
```
http://localhost:8080/api/v1
```

## Authentication Endpoints

### Register User
```http
POST /auth/register
Content-Type: application/json

{
  "email": "string",
  "password": "string",
  "name": "string"
}

Response: 201 CREATED
{
  "id": "string",
  "email": "string",
  "name": "string",
  "role": "USER"
}
```

### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "string",
  "password": "string"
}

Response: 200 OK
{
  "token": "string",
  "refreshToken": "string",
  "user": {
    "id": "string",
    "email": "string",
    "name": "string",
    "role": "string"
  }
}
```

### Refresh Token
```http
POST /auth/refresh
Authorization: Bearer {refreshToken}

Response: 200 OK
{
  "token": "string",
  "refreshToken": "string"
}
```

## SuperAdmin Endpoints

### Create SubAdmin
```http
POST /admin/subadmin
Authorization: Bearer {token}
Content-Type: application/json

{
  "email": "string",
  "password": "string",
  "name": "string"
}

Response: 201 CREATED
{
  "id": "string",
  "email": "string",
  "name": "string",
  "role": "SUB_ADMIN"
}
```

### List SubAdmins
```http
GET /admin/subadmins
Authorization: Bearer {token}

Response: 200 OK
{
  "subadmins": [
    {
      "id": "string",
      "email": "string",
      "name": "string"
    }
  ]
}
```

## SubAdmin Endpoints

### Create Team
```http
POST /teams
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "string",
  "members": [
    {
      "userId": "string",
      "role": "string"
    }
  ]
}

Response: 201 CREATED
{
  "id": "string",
  "name": "string",
  "members": [
    {
      "id": "string",
      "name": "string",
      "role": "string"
    }
  ]
}
```

### Assign Project
```http
POST /projects/assign
Authorization: Bearer {token}
Content-Type: application/json

{
  "projectId": "string",
  "teamId": "string"
}

Response: 200 OK
{
  "message": "Project assigned successfully"
}
```

## ProjectDeveloperTeam Endpoints

### Update Project Status
```http
PATCH /projects/{projectId}/status
Authorization: Bearer {token}
Content-Type: application/json

{
  "status": "string",
  "comment": "string"
}

Response: 200 OK
{
  "id": "string",
  "status": "string",
  "updatedAt": "string"
}
```

### Upload Deliverable
```http
POST /projects/{projectId}/deliverables
Authorization: Bearer {token}
Content-Type: multipart/form-data

file: [binary]
description: "string"

Response: 201 CREATED
{
  "id": "string",
  "filename": "string",
  "downloadUrl": "string"
}
```

## TeamMember Endpoints

### Schedule Interview
```http
POST /interviews
Authorization: Bearer {token}
Content-Type: application/json

{
  "userId": "string",
  "dateTime": "string",
  "type": "string"
}

Response: 201 CREATED
{
  "id": "string",
  "dateTime": "string",
  "status": "SCHEDULED"
}
```

### Upload Certificate
```http
POST /certificates
Authorization: Bearer {token}
Content-Type: multipart/form-data

file: [binary]
userId: "string"
title: "string"

Response: 201 CREATED
{
  "id": "string",
  "title": "string",
  "downloadUrl": "string"
}
```

## User Endpoints

### View Project Progress
```http
GET /projects/{projectId}/progress
Authorization: Bearer {token}

Response: 200 OK
{
  "id": "string",
  "title": "string",
  "status": "string",
  "milestones": [
    {
      "title": "string",
      "completedAt": "string"
    }
  ]
}
```

### Start Mock Test
```http
POST /mock-tests/{testId}/start
Authorization: Bearer {token}

Response: 200 OK
{
  "id": "string",
  "questions": [
    {
      "id": "string",
      "text": "string",
      "options": [
        {
          "id": "string",
          "text": "string"
        }
      ]
    }
  ],
  "duration": "number"
}
```

### Submit Mock Test
```http
POST /mock-tests/{testId}/submit
Authorization: Bearer {token}
Content-Type: application/json

{
  "answers": [
    {
      "questionId": "string",
      "selectedOptionId": "string"
    }
  ]
}

Response: 200 OK
{
  "score": "number",
  "totalQuestions": "number",
  "correctAnswers": "number"
}
```

## Error Responses

### 400 Bad Request
```json
{
  "error": "string",
  "message": "string",
  "timestamp": "string"
}
```

### 401 Unauthorized
```json
{
  "error": "Unauthorized",
  "message": "Invalid or expired token",
  "timestamp": "string"
}
```

### 403 Forbidden
```json
{
  "error": "Forbidden",
  "message": "Insufficient permissions",
  "timestamp": "string"
}
```

### 404 Not Found
```json
{
  "error": "Not Found",
  "message": "Resource not found",
  "timestamp": "string"
}
```

### 500 Internal Server Error
```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "timestamp": "string"
}
```