# Quick Reference & Cheat Sheet

Quick lookup guide for common tasks and commands in Arc-i-Tech Dashboard System.

## ⚡ Quick Commands

### Backend Commands

```bash
# Development
mvn clean install              # Install dependencies
mvn spring-boot:run            # Run in development
mvn test                        # Run tests

# Production
mvn clean package -DskipTests  # Build JAR for production
java -jar target/Arc-i-Tech_Backend-0.0.1-SNAPSHOT.jar  # Run JAR

# Specific test
mvn test -Dtest=TestClassName

# View help
mvn help:describe -Dplugin=spring-boot
```

### Frontend Commands

```bash
# Development
npm install                    # Install dependencies
npm run dev                    # Run dev server (port 3000)
npm run build                  # Build for production
npm start                      # Run production build
npm test                       # Run tests

# Linting & Formatting
npm run lint                   # Check TypeScript
npm run format                 # Format code

# Clean
rm -rf node_modules package-lock.json && npm install
```

### Database Commands

```bash
# Connect
mysql -u root -p              # Connect to MySQL

# Create Database
CREATE DATABASE arc_i_tech CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE arc_i_tech;

# Show Tables
SHOW TABLES;
DESCRIBE users;

# Backup
mysqldump -u root -p arc_i_tech > backup.sql

# Restore
mysql -u root -p arc_i_tech < backup.sql

# Check Connections
SHOW PROCESSLIST;
SHOW STATUS;
```

### System Commands

```bash
# Check Service Status
systemctl status arc-backend
systemctl start arc-backend
systemctl stop arc-backend
systemctl restart arc-backend

# View Logs
journalctl -u arc-backend -f
tail -f /var/log/arc-backend.log

# Check Port Usage
netstat -tuln | grep 8080
lsof -i :8080

# Kill Process
kill -9 <PID>
```

---

## 🔐 Authentication Quick Start

### Login Request
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

### API Request with Token
```bash
curl -X GET http://localhost:8080/api/v1/dashboard/super-admin?userId=1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

### Token Refresh
Get new token via login endpoint (no refresh token endpoint available)

---

## 📡 API Endpoints Quick Reference

### Authentication Endpoints
```
POST   /auth/login              # User login
POST   /auth/register           # User registration
```

### Dashboard Endpoints (GET)
```
GET    /dashboard/super-admin         # @PreAuthorize("hasRole('SUPER_ADMIN')")
GET    /dashboard/sub-admin           # @PreAuthorize("hasRole('SUB_ADMIN')")
GET    /dashboard/project-developer   # @PreAuthorize("hasRole('PROJECT_DEVELOPER_TEAM')")
GET    /dashboard/team-member         # @PreAuthorize("hasRole('TEAM_MEMBER')")
GET    /dashboard/user                # @PreAuthorize("hasRole('SOFTWARE_USER')")
```

### Team Endpoints
```
GET    /teams                    # List teams
POST   /teams                    # Create team
GET    /teams/{id}               # Get team details
POST   /teams/{id}/members       # Add team member
DELETE /teams/{id}/members/{uid} # Remove team member
```

### Project Endpoints
```
GET    /projects                 # List projects
POST   /projects                 # Create project
GET    /projects/{id}            # Get project details
PUT    /projects/{id}/progress   # Update progress
```

### Training Endpoints
```
POST   /training/sessions        # Create session
GET    /training/sessions        # List sessions
POST   /certificates             # Issue certificate
```

### Approval Endpoints
```
POST   /approvals/project-updates/{id}/approve    # Approve
POST   /approvals/project-updates/{id}/reject     # Reject
POST   /approvals/certificates/{id}/approve       # Approve
```

---

## 🎯 HTTP Status Codes

| Code | Meaning | Common Cause |
|------|---------|-------------|
| 200 | OK | Successful GET, PUT |
| 201 | Created | Successful POST |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Invalid parameters |
| 401 | Unauthorized | Missing/invalid token |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource doesn't exist |
| 409 | Conflict | Resource already exists |
| 500 | Server Error | Unexpected error |

---

## 👥 User Roles

| Role | ID | Dashboard | Permissions |
|------|----|-----------| ------------|
| SUPER_ADMIN | 1 | Super Admin Dashboard | Full system access |
| SUB_ADMIN | 2 | Sub Admin Dashboard | Team/project management |
| PROJECT_DEVELOPER_TEAM | 3 | Project Developer Dashboard | Project updates |
| TEAM_MEMBER | 4 | Team Member Dashboard | Student management |
| SOFTWARE_USER | 5 | User Dashboard | Project access |
| TRAINING_USER | 6 | User Dashboard | Training access |

---

## 📁 Key File Locations

### Backend
```
Controllers:        Arc-i-Tech_Backend/src/main/java/com/arcitech/controller/
Services:           Arc-i-Tech_Backend/src/main/java/com/arcitech/service/
Models:             Arc-i-Tech_Backend/src/main/java/com/arcitech/model/
Repositories:       Arc-i-Tech_Backend/src/main/java/com/arcitech/repository/
DTOs:               Arc-i-Tech_Backend/src/main/java/com/arcitech/dto/
Config:             Arc-i-Tech_Backend/src/main/resources/application.properties
```

### Frontend
```
Pages:              arc-i-tech_frontend/app/
Components:         arc-i-tech_frontend/components/
Store:              arc-i-tech_frontend/store/
Config:             arc-i-tech_frontend/next.config.ts
Styles:             arc-i-tech_frontend/app/globals.css
```

### Configuration
```
Backend:            Arc-i-Tech_Backend/pom.xml
Frontend:           arc-i-tech_frontend/package.json
TypeScript:         arc-i-tech_frontend/tsconfig.json
Tailwind:           arc-i-tech_frontend/tailwind.config.js
```

---

## 🔧 Environment Variables

### Backend (application.properties)
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/arc_i_tech
spring.datasource.username=root
spring.datasource.password=password

# JWT
jwt.secret=your_secret_key_minimum_256_bits_long
jwt.expiration=86400000

# Server
server.port=8080
spring.application.name=Arc-i-Tech-Backend
```

### Frontend (.env.local)
```env
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080/api/v1
NEXT_PUBLIC_APP_NAME=Arc-i-Tech Dashboard
NEXT_PUBLIC_ENABLE_ANALYTICS=true
NEXT_PUBLIC_ENABLE_NOTIFICATIONS=true
```

---

## 🎨 Component Structure Templates

### Java Service Class
```java
@Service
@RequiredArgsConstructor
@Transactional
public class MyService {
    private final MyRepository myRepository;
    
    @Override
    public MyDTO getMyData(String id) {
        return myRepository.findById(Long.parseLong(id))
            .map(this::convertToDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
}
```

### React Component
```typescript
import { FC } from 'react';

interface Props {
  title: string;
  onClick?: () => void;
}

const MyComponent: FC<Props> = ({ title, onClick }) => {
  return <div onClick={onClick}>{title}</div>;
};

export default MyComponent;
```

### Zustand Store
```typescript
import { create } from 'zustand';

interface StoreState {
  count: number;
  increment: () => void;
}

export const useStore = create<StoreState>((set) => ({
  count: 0,
  increment: () => set((state) => ({ count: state.count + 1 })),
}));
```

---

## 🐛 Common Errors & Fixes

| Error | Solution |
|-------|----------|
| `java: command not found` | Install Java 17 and set JAVA_HOME |
| `mvn: command not found` | Install Maven and add to PATH |
| `npm ERR! 404` | Run `npm cache clean --force` |
| `Cannot find module 'next'` | Run `npm install` in frontend folder |
| `Connection refused: localhost:8080` | Start backend: `mvn spring-boot:run` |
| `Connection refused: localhost:3000` | Start frontend: `npm run dev` |
| `Connection refused: localhost:3306` | Start MySQL: `systemctl start mysql` |
| `401 Unauthorized` | Check JWT token in Authorization header |
| `403 Forbidden` | Verify user role has permission |
| `404 Not Found` | Check endpoint URL and HTTP method |

---

## 📊 Database Tables

### users
```sql
id, email, password, name, role_id, active, created_at, updated_at
```

### teams
```sql
id, name, team_type, created_by, active, created_at, updated_at
```

### projects
```sql
id, title, description, status, progress, team_id, client_id, start_date, end_date, created_at, updated_at
```

### certificates
```sql
id, user_id, title, issued_date, approved, rejection_reason, file_url, created_at
```

---

## 🚀 Deployment Cheat Sheet

### Build & Package
```bash
# Backend
mvn clean package -DskipTests -P production

# Frontend
npm run build
```

### Docker
```bash
# Build images
docker build -t arc-backend ./Arc-i-Tech_Backend
docker build -t arc-frontend ./arc-i-tech_frontend

# Run containers
docker run -p 8080:8080 arc-backend
docker run -p 3000:3000 arc-frontend

# Docker Compose
docker-compose up -d
```

### Systemd Service
```bash
# Enable and start
sudo systemctl enable arc-backend
sudo systemctl start arc-backend

# View status
sudo systemctl status arc-backend

# View logs
sudo journalctl -u arc-backend -f
```

### Nginx
```bash
# Test config
sudo nginx -t

# Reload
sudo systemctl reload nginx

# View logs
sudo tail -f /var/log/nginx/arc-api-access.log
```

---

## 🧪 Testing Quick Commands

### Backend
```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=UserDashboardServiceImplTest

# With coverage
mvn clean test jacoco:report
```

### Frontend
```bash
# All tests
npm test

# Watch mode
npm test -- --watch

# Coverage
npm test -- --coverage
```

---

## 📈 Performance Optimization Tips

### Backend
```bash
# Check memory usage
jstat -gc <PID> 1000

# Enable slow query log
SET GLOBAL slow_query_log = 'ON';

# Add database index
CREATE INDEX idx_email ON users(email);
```

### Frontend
```bash
# Analyze bundle
npm run build -- --analyze

# Check performance
npm run build
npm start
```

### Database
```bash
# Optimize tables
OPTIMIZE TABLE users;

# Check query performance
EXPLAIN SELECT * FROM projects WHERE status='active';
```

---

## 🔄 Git Commands

### Basic Workflow
```bash
# Clone
git clone <repository>

# Create branch
git checkout -b feature/new-feature

# Commit
git add .
git commit -m "Add new feature"

# Push
git push origin feature/new-feature

# Create Pull Request
# (on GitHub/GitLab website)
```

### Useful Commands
```bash
# Check status
git status

# View log
git log --oneline

# Stash changes
git stash

# Switch branch
git checkout main
```

---

## 📞 Common Support Tasks

### How to add a new role?
1. Add role in `User` model
2. Create new service implementation
3. Create new controller
4. Add `@PreAuthorize("hasRole('...')")` guard

### How to add a new API endpoint?
1. Add method in service interface
2. Implement in service class
3. Add mapping in controller
4. Document in API_DOCUMENTATION.md

### How to connect to production database?
1. Update `application-production.properties`
2. Add to environment variables
3. Restart application
4. Verify connection in logs

### How to enable caching?
```properties
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379
```

---

## 📚 Documentation Quick Links

- **Full README**: README.md
- **Setup Guide**: SETUP.md
- **API Docs**: API_DOCUMENTATION.md
- **Architecture**: ARCHITECTURE.md
- **Deployment**: DEPLOYMENT.md
- **This Guide**: QUICK_REFERENCE.md

---

## ⏱️ Typical Development Workflow

### Day 1: Setup
1. Clone repository
2. Follow SETUP.md
3. Verify everything works
4. Explore project structure

### Days 2-5: Development
1. Pick a feature from backlog
2. Create feature branch
3. Write code following ARCHITECTURE.md patterns
4. Write tests
5. Create pull request

### Week 2+: Integration
1. Test with other components
2. Test with frontend
3. Deploy to staging
4. Performance testing

---

## 🎯 Checklist for New Features

- [ ] Create feature branch
- [ ] Write unit tests
- [ ] Follow code style guide
- [ ] Add API documentation
- [ ] Update database schema (if needed)
- [ ] Add frontend component
- [ ] Integration testing
- [ ] Create pull request
- [ ] Code review
- [ ] Merge to main
- [ ] Deploy to staging
- [ ] Deploy to production

---

## 📞 Emergency Contacts

- **Backend Issues**: backend-team@arc-i-tech.com
- **Frontend Issues**: frontend-team@arc-i-tech.com
- **Database Issues**: dba@arc-i-tech.com
- **DevOps/Infrastructure**: devops@arc-i-tech.com

---

## 🔐 Security Checklist

- [ ] JWT token configured securely
- [ ] HTTPS/SSL enabled in production
- [ ] Database credentials in environment variables
- [ ] CORS properly configured
- [ ] Input validation implemented
- [ ] SQL injection protection enabled
- [ ] Rate limiting configured
- [ ] Error messages sanitized
- [ ] Secrets not in version control
- [ ] Regular security updates applied

---

**Last Updated**: November 11, 2025  
**Version**: 1.0.0  
**Quick Reference Type**: Developer Cheat Sheet

---

**Print this page or bookmark for quick access!** 🎯
