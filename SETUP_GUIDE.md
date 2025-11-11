# Arc-i-Tech Platform - Setup & Run Guide

## Summary of Fixes Applied

### Backend (Java/Spring Boot) Fixes:
1. **DashboardController.java**
   - Fixed: Changed from non-existent `DashboardService` to individual dashboard services
   - Updated endpoint paths from `/dashboard` to `/api/v1/dashboard`
   - Added role-based authorization for all endpoints

2. **ProjectDeveloperDashboardServiceImpl.java**
   - Added: Missing interface method implementations
   - Implemented: `addProjectNote()`, `sendMessageToSubAdmin()` methods
   - Made `getApprovals()` and `getFeedback()` public to match interface
   - Added missing imports for `HashMap` and `Map`

3. **TeamMemberDashboardServiceImpl.java**
   - Added: Missing interface method implementations
   - Implemented: `sendMessageToSubAdmin()` method
   - Added missing imports for `HashMap` and `Map`

4. **SecurityConfig.java**
   - Removed unused import of `com.arcitech.model.Project`

### Frontend (Next.js/React) Fixes:
1. **Component Imports**
   - Fixed case-sensitivity issues with Sidebar/Navbar imports
   - Created missing UI component implementations: `avatar.tsx`, `scroll-area.tsx`
   - Fixed icon type definitions to avoid lucide-react import errors

2. **Zustand Store (auth.ts)**
   - Added missing user roles: `SOFTWARE_USER`, `TRAINING_USER`
   - Added `clearAuth()` method to `AuthState` interface

3. **Route Guards & Hooks**
   - Fixed `use-role.ts` hook with proper imports
   - Updated `unauthorized/page.tsx` with proper role routing

4. **Dashboard Components**
   - Fixed `stats-card.tsx` to use local icon type instead of lucide-react export
   - Fixed `quick-actions.tsx` similarly
   - Updated `activity-feed.tsx` imports for Avatar components

5. **Dependencies (package.json)**
   - Updated `@radix-ui/react-scroll-area` to `^1.0.5`
   - Downgraded `@types/react` to `^18.2.0` for compatibility
   - Downgraded `@types/react-dom` to `^18.2.0` for compatibility

## Prerequisites

### Backend Requirements:
- Java 17 or higher
- Maven 3.8+
- MySQL Server 8.0+

### Frontend Requirements:
- Node.js 18+
- npm 9+

## Backend Setup & Run

### 1. Configure Database
```bash
# Update application.properties with your MySQL credentials
# File: Arc-i-Tech_Backend/src/main/resources/application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/arc_i_tech
spring.datasource.username=root
spring.datasource.password=your_password
```

### 2. Build the Backend
```bash
cd Arc-i-Tech_Backend
./mvnw clean package -DskipTests
```

### 3. Run the Backend
```bash
./mvnw spring-boot:run
```

The backend will start on: `http://localhost:8080`

## Frontend Setup & Run

### 1. Install Dependencies
```bash
cd arc-i-tech_frontend
npm install --legacy-peer-deps
```

### 2. Configure API Endpoint (if needed)
```bash
# Create a .env.local file in the frontend root:
NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1
```

### 3. Run Development Server
```bash
npm run dev
```

The frontend will start on: `http://localhost:3000`

## API Endpoints (Primary)

### Authentication
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/login` - User login

### Dashboard (Role-Based)
- `GET /api/v1/dashboard/super-admin` - Super Admin dashboard
- `GET /api/v1/dashboard/sub-admin` - Sub Admin dashboard
- `GET /api/v1/dashboard/project-developer` - Developer dashboard
- `GET /api/v1/dashboard/team-member` - Team Member dashboard
- `GET /api/v1/dashboard/user` - User/Software/Training dashboard

## Frontend Routes (Protected)

### Public Routes
- `/login` - Login page
- `/register` - Registration page

### Dashboard Routes (Role-Based)
- `/super-admin/dashboard` - Super Admin dashboard
- `/sub-admin/dashboard` - Sub Admin dashboard
- `/developer/dashboard` - Developer dashboard
- `/team-member/dashboard` - Team Member dashboard
- `/user/dashboard` - User dashboard

### Additional Routes
- `/unauthorized` - Access denied page

## Supported User Roles

1. **SUPER_ADMIN** - System-wide management
2. **SUB_ADMIN** - Department/Organization management
3. **PROJECT_DEVELOPER_TEAM** - Development team lead
4. **TEAM_MEMBER** - Individual team member
5. **SOFTWARE_USER** - Regular software user
6. **TRAINING_USER** - Training/Learning user

## Known Limitations & Notes

### Frontend TypeScript Declarations
- Some Next.js modules may show TypeScript declaration warnings in the editor
- These are cosmetic and do NOT affect runtime functionality
- The application will build and run successfully despite these warnings

### Backend DTOs
- Many DTOs have placeholder implementations
- Extend these with your actual business logic as needed

## Running Tests

### Backend Tests
```bash
cd Arc-i-Tech_Backend
./mvnw test
```

### Frontend Tests (if configured)
```bash
cd arc-i-tech_frontend
npm run test
```

## Docker Deployment (Optional)

### Backend Docker
```bash
cd Arc-i-Tech_Backend
docker build -t arc-i-tech-backend .
docker run -p 8080:8080 arc-i-tech-backend
```

### Frontend Docker
```bash
cd arc-i-tech_frontend
docker build -t arc-i-tech-frontend .
docker run -p 3000:3000 arc-i-tech-frontend
```

## Troubleshooting

### Backend Issues
1. **Maven build fails**: Ensure Java 17 is installed and JAVA_HOME is set
2. **Database connection fails**: Check MySQL is running and credentials are correct
3. **Port 8080 already in use**: Kill the process or change the port in `application.properties`

### Frontend Issues
1. **npm install fails**: Try `npm install --legacy-peer-deps`
2. **Next.js dev server won't start**: Delete `.next` folder and node_modules, then reinstall
3. **Type errors in editor**: These are often resolved on next save; VSCode caches can be cleared

## Support & Documentation

- Backend Framework: [Spring Boot Docs](https://spring.io/projects/spring-boot)
- Frontend Framework: [Next.js Docs](https://nextjs.org/docs)
- UI Components: [shadcn/ui](https://ui.shadcn.com/)
- State Management: [Zustand](https://github.com/pmndrs/zustand)

---

**Last Updated**: November 11, 2025
**Status**: All critical errors resolved; Application ready for development
