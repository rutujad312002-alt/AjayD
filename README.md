# Arc-i-Tech Dashboard System

A comprehensive role-based dashboard application built with Spring Boot, Next.js, and React. This system provides specialized dashboards for multiple user roles including Super Admin, Sub-Admin, Project Developers, Team Members, and Training Users.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [User Roles & Permissions](#user-roles--permissions)
- [Features](#features)
- [Development Guidelines](#development-guidelines)

## 🎯 Project Overview

Arc-i-Tech is a full-stack web application that provides role-based dashboard functionality for managing projects, teams, training, certifications, and user interactions. The system implements a comprehensive authorization system with specific dashboards and permissions for each user role.

### Key Features:
- **Role-Based Access Control (RBAC)**: 6 distinct user roles with unique permissions
- **Multi-Service Architecture**: Specialized services for different user types
- **Real-Time Dashboards**: Dynamic data visualization and analytics
- **Project Management**: Track projects, tasks, and deployments
- **Training Management**: Manage training sessions, certifications, and skill progress
- **Team Collaboration**: Team management, meetings, and inquiries

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **Build Tool**: Maven
- **Database**: MySQL 8.0
- **ORM**: JPA/Hibernate
- **Authentication**: JWT (JJWT 0.11.5)
- **Security**: Spring Security
- **Utilities**: Lombok

### Frontend
- **Framework**: Next.js 14.0.3
- **UI Library**: React 18.2.0
- **Language**: TypeScript 5.0
- **Styling**: Tailwind CSS 3.3.5
- **Components**: shadcn/ui
- **State Management**: Zustand
- **Charts**: Chart.js
- **HTTP Client**: Axios
- **Icons**: Lucide React

## 🏗️ Architecture

```
Arc-i-Tech Dashboard System
├── Backend (Spring Boot)
│   ├── Controller Layer (REST Endpoints)
│   ├── Service Layer (Business Logic)
│   ├── Repository Layer (Data Access)
│   └── Model Layer (Entity Definitions)
│
└── Frontend (Next.js)
    ├── Pages (Route Components)
    ├── Components (Reusable UI)
    ├── Store (Zustand State Management)
    └── Hooks (Custom React Hooks)
```

## 📁 Project Structure

```
Arc-i-Tech_Backend/
├── src/main/java/com/arcitech/
│   ├── controller/          # REST API endpoints
│   ├── service/             # Business logic interfaces
│   ├── service/impl/        # Service implementations
│   ├── repository/          # Data access layer
│   ├── model/               # JPA entity models
│   ├── dto/                 # Data Transfer Objects
│   ├── config/              # Spring configuration
│   └── security/            # Security & JWT handling
├── resources/
│   └── application.properties
└── pom.xml

arc-i-tech_frontend/
├── app/
│   ├── layout.tsx           # Root layout
│   ├── page.tsx             # Home page
│   └── (dashboard)/         # Dashboard routes
├── components/
│   ├── ui/                  # UI components
│   ├── dashboard/           # Dashboard components
│   └── layout/              # Layout components
├── store/                   # Zustand stores
├── hooks/                   # Custom hooks
├── public/                  # Static assets
├── package.json
└── tsconfig.json
```

## 🚀 Installation & Setup

### Prerequisites
- Java 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.8+

### Backend Setup

1. **Clone the Repository**
```bash
cd Arc-i-Tech_Backend
```

2. **Configure Database**
   - Create a MySQL database named `arc_i_tech`
   - Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/arc_i_tech
spring.datasource.username=root
spring.datasource.password=your_password
```

3. **Build the Backend**
```bash
mvn clean install
```

### Frontend Setup

1. **Navigate to Frontend Directory**
```bash
cd arc-i-tech_frontend
```

2. **Install Dependencies**
```bash
npm install
```

3. **Configure Environment**
   - Create `.env.local` file:
```env
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
```

## 🏃 Running the Application

### Backend (Spring Boot)

```bash
# From Arc-i-Tech_Backend directory
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

**API Health Check**: `http://localhost:8080/health`

### Frontend (Next.js)

```bash
# From arc-i-tech_frontend directory
npm run dev
```

The frontend will start on `http://localhost:3000`

**Access the Application**: Open `http://localhost:3000` in your browser

## 📚 API Documentation

### Authentication Endpoints

#### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

Response:
```json
{
  "token": "jwt_token_here",
  "user": {
    "id": 1,
    "name": "User Name",
    "email": "user@example.com",
    "role": "SUPER_ADMIN"
  }
}
```

### Dashboard Endpoints

#### Super Admin Dashboard
```http
GET /api/v1/dashboard/super-admin?userId=1
Authorization: Bearer {token}
```

#### Sub Admin Dashboard
```http
GET /api/v1/dashboard/sub-admin?userId=2
Authorization: Bearer {token}
```

#### Project Developer Dashboard
```http
GET /api/v1/dashboard/project-developer?userId=3
Authorization: Bearer {token}
```

#### Team Member Dashboard
```http
GET /api/v1/dashboard/team-member?userId=4
Authorization: Bearer {token}
```

#### User Dashboard
```http
GET /api/v1/dashboard/user?userId=5
Authorization: Bearer {token}
```

## 💾 Database Schema

### Core Tables

**users** - User accounts and authentication
```sql
- id (PK)
- email (UNIQUE)
- password
- name
- role_id (FK)
- active
- created_at
- updated_at
```

**teams** - Team organization
```sql
- id (PK)
- name
- team_type
- created_by (FK)
- active
- created_at
- updated_at
```

**projects** - Project management
```sql
- id (PK)
- title
- description
- status
- progress
- team_id (FK)
- client_id (FK)
- start_date
- end_date
- created_at
- updated_at
```

**certificates** - User certifications
```sql
- id (PK)
- user_id (FK)
- title
- issued_date
- approved
- rejection_reason
- file_url
- created_at
```

## 👥 User Roles & Permissions

### 1. SUPER_ADMIN
- Full system access
- User management
- System analytics and reporting
- Backup and restore operations
- Sub-admin creation and management

**Dashboard Access**: Super Admin Dashboard

### 2. SUB_ADMIN
- Team management
- User approvals
- Project assignment
- Reporting and analytics for managed teams

**Dashboard Access**: Sub Admin Dashboard

### 3. PROJECT_DEVELOPER_TEAM
- Project and task management
- Deployment tracking
- Team collaboration
- Project updates and reporting

**Dashboard Access**: Project Developer Dashboard

### 4. TEAM_MEMBER
- Student management
- Training session tracking
- Mock interviews and assessments
- Certification management
- Performance monitoring

**Dashboard Access**: Team Member Dashboard

### 5. SOFTWARE_USER
- Project access and monitoring
- Deliverable tracking
- Timeline viewing
- Progress updates

**Dashboard Access**: User Dashboard

### 6. TRAINING_USER
- Training session access
- Learning material access
- Mock test participation
- Certification viewing
- Skill progress tracking

**Dashboard Access**: User Dashboard

## ✨ Features

### 🎨 Dashboard Features

- **Real-time Statistics**: Key metrics and KPIs
- **Activity Feeds**: Recent user activities
- **Data Visualization**: Charts and graphs using Chart.js
- **Quick Actions**: Common operations shortcuts
- **Notifications**: System and user notifications
- **Responsive Design**: Mobile-friendly interface

### 👥 Team Management

- Create and manage teams
- Add/remove team members
- Assign projects to teams
- Track team performance

### 📊 Project Management

- Create and update projects
- Track project progress
- Manage tasks and deployments
- Monitor status updates
- Generate project reports

### 🎓 Training & Certification

- Schedule training sessions
- Track skill progress
- Manage certifications
- Mock interview scheduling
- Performance analytics

### 📈 Analytics & Reporting

- System-wide analytics
- Team performance metrics
- User activity tracking
- Audit logs
- Export reports

## 📖 Development Guidelines

### Code Structure

#### Service Layer Pattern
```java
// Interface
public interface DashboardService<T> {
    T getDashboardData(String userId);
}

// Implementation
@Service
@RequiredArgsConstructor
public class SuperAdminDashboardServiceImpl implements SuperAdminDashboardService {
    private final UserRepository userRepository;
    // Implementation methods
}
```

#### DTO Pattern
```java
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
    private Long totalUsers;
    private Long totalProjects;
    // Other fields
}
```

### Adding New Features

1. **Create Model/Entity**
```bash
src/main/java/com/arcitech/model/YourEntity.java
```

2. **Create Repository**
```bash
src/main/java/com/arcitech/repository/YourEntityRepository.java
```

3. **Create DTO**
```bash
src/main/java/com/arcitech/dto/YourEntityDTO.java
```

4. **Create Service Interface & Implementation**
```bash
src/main/java/com/arcitech/service/YourService.java
src/main/java/com/arcitech/service/impl/YourServiceImpl.java
```

5. **Create Controller**
```bash
src/main/java/com/arcitech/controller/YourController.java
```

### Frontend Component Pattern

```typescript
import { FC } from 'react';

interface ComponentProps {
  title: string;
  // Other props
}

const YourComponent: FC<ComponentProps> = ({ title }) => {
  return (
    <div>
      {/* Component JSX */}
    </div>
  );
};

export default YourComponent;
```

## 🔒 Security

- **JWT Authentication**: Token-based authentication
- **Role-Based Access Control**: @PreAuthorize annotations
- **CORS Configuration**: Secured cross-origin requests
- **SQL Injection Protection**: Parameterized queries via JPA
- **Password Encoding**: BCrypt password hashing

## 🧪 Testing

### Backend Tests
```bash
mvn test
```

### Frontend Tests
```bash
npm run test
```

## 📝 Build & Deployment

### Building for Production

**Backend**:
```bash
mvn clean package -DskipTests
```

**Frontend**:
```bash
npm run build
npm start
```

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `application.properties`
   - Ensure database `arc_i_tech` exists

2. **Port Already in Use**
   - Change port in `application.properties`: `server.port=8081`
   - Change frontend port: `npm run dev -- -p 3001`

3. **CORS Errors**
   - Verify CORS configuration in backend
   - Check frontend API URL in `.env.local`

4. **Missing Dependencies**
   - Backend: `mvn clean install`
   - Frontend: `npm install`

## 📞 Support & Contact

For issues or questions:
- Create an issue in the repository
- Contact: [Your Email]
- Documentation: Check `/docs` folder

## 📄 License

This project is licensed under the MIT License - see LICENSE file for details.

## 🙏 Acknowledgments

- Spring Boot team for excellent framework
- Next.js and React communities
- All contributors and team members

---

**Last Updated**: November 11, 2025
**Version**: 1.0.0
**Status**: ✅ Production Ready
