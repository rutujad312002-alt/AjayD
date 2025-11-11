# Installation & Setup Guide

Complete step-by-step guide for setting up the Arc-i-Tech Dashboard System locally.

## 📋 Table of Contents

- [System Requirements](#system-requirements)
- [Backend Setup](#backend-setup)
- [Frontend Setup](#frontend-setup)
- [Database Configuration](#database-configuration)
- [Environment Configuration](#environment-configuration)
- [Verification & Testing](#verification--testing)
- [Troubleshooting](#troubleshooting)

## 💻 System Requirements

### Minimum Requirements
- **RAM**: 8GB
- **Disk Space**: 5GB
- **OS**: Windows 10/11, macOS 10.15+, or Linux (Ubuntu 20.04+)

### Required Software

#### Java & Build Tools
- **Java Development Kit (JDK)**: Version 17 or higher
  - [Download JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
  - Verify installation: `java -version`

- **Apache Maven**: Version 3.8.0 or higher
  - [Download Maven](https://maven.apache.org/download.cgi)
  - Verify installation: `mvn -version`

#### Node.js & Package Manager
- **Node.js**: Version 18.0.0 or higher
  - [Download Node.js](https://nodejs.org/)
  - Includes npm (Node Package Manager)
  - Verify installation: `node -v` and `npm -v`

#### Database
- **MySQL**: Version 8.0 or higher
  - [Download MySQL](https://dev.mysql.com/downloads/mysql/)
  - Community Edition is sufficient
  - Verify installation: `mysql -v`

#### IDE (Optional but Recommended)
- **IntelliJ IDEA**: Ultimate or Community Edition
  - [Download IntelliJ](https://www.jetbrains.com/idea/)
- **VS Code**: Latest version
  - [Download VS Code](https://code.visualstudio.com/)

## 🔧 Backend Setup

### Step 1: Prepare Java Environment

```bash
# Verify Java installation
java -version

# Output should show:
# openjdk version "17.0.x"
```

If Java is not installed or version is incorrect, set `JAVA_HOME`:

**Windows**:
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

**Linux/macOS**:
```bash
export JAVA_HOME=/usr/local/java/jdk-17
export PATH=$JAVA_HOME/bin:$PATH
```

### Step 2: Clone & Navigate to Backend

```bash
cd Arc-i-Tech_Backend
```

### Step 3: Install Dependencies

```bash
# Clean previous builds and install dependencies
mvn clean install
```

This will:
- Download all required dependencies
- Build the project
- Run initial tests
- Create the `target/` directory

Expected output:
```
[INFO] BUILD SUCCESS
```

### Step 4: Configure Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/api/v1

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/arc_i_tech?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password_here
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT Configuration
jwt.secret=your_secret_key_here_minimum_256_bits_long
jwt.expiration=86400000

# Logging
logging.level.root=INFO
logging.level.com.arcitech=DEBUG

# Application Name
spring.application.name=Arc-i-Tech-Backend
spring.boot.version=3.1.5
```

### Step 5: Build the Backend

```bash
mvn clean package -DskipTests
```

This creates `target/Arc-i-Tech_Backend-0.0.1-SNAPSHOT.jar`

### Step 6: Run Backend

#### Using Maven:
```bash
mvn spring-boot:run
```

#### Using JAR file:
```bash
java -jar target/Arc-i-Tech_Backend-0.0.1-SNAPSHOT.jar
```

**Expected Output**:
```
Started ArcITechBackendApplication in X seconds
[main] o.s.b.a.s.s.UserDetailsServiceAutoConfiguration
Using generated security password: XXXXXXXXXXXX
```

**Verify Backend**:
```bash
# In a new terminal
curl http://localhost:8080/api/v1/health
```

Expected response: `{"status":"UP"}`

---

## 🎨 Frontend Setup

### Step 1: Navigate to Frontend Directory

```bash
cd arc-i-tech_frontend
```

### Step 2: Verify Node.js Installation

```bash
node -v
npm -v

# Should output versions like:
# v18.17.0
# 8.19.2
```

### Step 3: Install Dependencies

```bash
npm install
```

This will:
- Install all packages from `package.json`
- Create `node_modules/` directory
- Generate `package-lock.json`

Expected output:
```
added XXX packages in XXXs
```

### Step 4: Create Environment Configuration

Create `.env.local` file in `arc-i-tech_frontend/`:

```env
# API Configuration
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080/api/v1

# Application Configuration
NEXT_PUBLIC_APP_NAME=Arc-i-Tech Dashboard
NEXT_PUBLIC_APP_VERSION=1.0.0

# Features
NEXT_PUBLIC_ENABLE_ANALYTICS=true
NEXT_PUBLIC_ENABLE_NOTIFICATIONS=true
```

### Step 5: Verify Configuration Files

Ensure these files exist:
- `next.config.ts` - Next.js configuration
- `tsconfig.json` - TypeScript configuration
- `tailwind.config.js` - Tailwind CSS configuration
- `postcss.config.mjs` - PostCSS configuration

### Step 6: Build Frontend (Optional)

```bash
npm run build
```

Creates `.next/` directory with optimized production build.

### Step 7: Run Frontend Development Server

```bash
npm run dev
```

Expected output:
```
▲ Next.js X.X.X
- Local:        http://localhost:3000
```

**Access Application**: Open `http://localhost:3000` in your browser

---

## 💾 Database Configuration

### Step 1: Start MySQL Server

**Windows (using MySQL command line)**:
```bash
mysql -u root -p
```

**Linux/macOS**:
```bash
mysql -u root -p
```

Or ensure MySQL service is running:

**Windows (PowerShell as Admin)**:
```powershell
Start-Service MySQL80
```

**Linux**:
```bash
sudo systemctl start mysql
```

### Step 2: Create Database

```sql
-- Connect to MySQL
mysql -u root -p

-- Create database
CREATE DATABASE arc_i_tech CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Verify creation
SHOW DATABASES;

-- Switch to database
USE arc_i_tech;
```

### Step 3: Create Application User (Optional but Recommended)

```sql
-- Create user
CREATE USER 'arc_tech'@'localhost' IDENTIFIED BY 'secure_password_123';

-- Grant privileges
GRANT ALL PRIVILEGES ON arc_i_tech.* TO 'arc_tech'@'localhost';

-- Apply changes
FLUSH PRIVILEGES;

-- Verify
SELECT User, Host FROM mysql.user;
```

Update `application.properties`:
```properties
spring.datasource.username=arc_tech
spring.datasource.password=secure_password_123
```

### Step 4: Verify Database Connection

From backend startup logs, look for:
```
Hibernate: create table users (...)
Hibernate: create table teams (...)
```

Or connect manually:
```bash
mysql -u root -p arc_i_tech -e "SHOW TABLES;"
```

---

## 🔐 Environment Configuration

### JWT Configuration

Generate a secure JWT secret key:

**Using Python**:
```python
import secrets
key = secrets.token_urlsafe(256)
print(key)
```

**Using OpenSSL**:
```bash
openssl rand -base64 256
```

Add to `application.properties`:
```properties
jwt.secret=<generated_key>
jwt.expiration=86400000
```

### Security Configuration

Update `application.properties`:
```properties
# CORS Configuration
cors.allowed-origins=http://localhost:3000,http://localhost:8080
cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
cors.allowed-headers=Content-Type,Authorization
cors.allow-credentials=true

# Security
security.jwt.header=Authorization
security.jwt.prefix=Bearer 
security.jwt.expiration=86400
```

---

## ✅ Verification & Testing

### Backend Verification

```bash
# 1. Check server is running
curl -i http://localhost:8080

# 2. Check health endpoint
curl http://localhost:8080/api/v1/health

# 3. Test API (requires Bearer token)
curl -H "Authorization: Bearer YOUR_TOKEN" \
  http://localhost:8080/api/v1/dashboard/super-admin?userId=1
```

### Frontend Verification

1. **Open Browser**: Navigate to `http://localhost:3000`
2. **Check Console**: Open DevTools (F12) → Console tab
3. **Check Network**: Monitor API calls to backend
4. **Verify Features**:
   - Navigation works
   - Dashboard loads
   - No console errors

### Database Verification

```bash
# Connect to database
mysql -u root -p arc_i_tech

# Check created tables
SHOW TABLES;

# View table structure
DESCRIBE users;
DESCRIBE teams;
DESCRIBE projects;
```

---

## 🧪 Running Tests

### Backend Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ArcITechBackendApplicationTests

# Run with coverage
mvn clean test jacoco:report
```

Test reports: `target/surefire-reports/`

### Frontend Tests

```bash
# Run all tests
npm test

# Run tests in watch mode
npm test -- --watch

# Run with coverage
npm test -- --coverage
```

---

## 🐛 Troubleshooting

### Backend Issues

#### Port 8080 Already in Use

```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill process (Windows)
taskkill /PID <PID> /F

# Or change port in application.properties
server.port=8081
```

#### Database Connection Failed

```properties
# Ensure correct URL and credentials
spring.datasource.url=jdbc:mysql://localhost:3306/arc_i_tech
spring.datasource.username=root
spring.datasource.password=correct_password
```

Verify MySQL is running:
```bash
mysql -u root -p -e "SELECT 1;"
```

#### Maven Build Failures

```bash
# Clear cache and rebuild
mvn clean install -U

# Skip tests temporarily
mvn clean install -DskipTests
```

### Frontend Issues

#### npm install Errors

```bash
# Clear cache
npm cache clean --force

# Delete node_modules
rm -rf node_modules package-lock.json

# Reinstall
npm install
```

#### Port 3000 Already in Use

```bash
# Run on different port
npm run dev -- -p 3001
```

#### TypeScript Errors

```bash
# Rebuild TypeScript
npm run build

# Check types
npx tsc --noEmit
```

### Common Error Messages

| Error | Solution |
|-------|----------|
| "java: command not found" | Install Java 17 and set JAVA_HOME |
| "mvn: command not found" | Install Maven and add to PATH |
| "EACCES: permission denied" | Run with sudo or check permissions |
| "Cannot find module 'next'" | Run `npm install` in frontend directory |
| "Connection refused: localhost:8080" | Ensure backend is running |
| "Connection refused: localhost:3306" | Ensure MySQL is running |

---

## 📝 Post-Setup Checklist

- [ ] Java 17+ installed and verified
- [ ] Maven installed and verified
- [ ] Node.js 18+ installed and verified
- [ ] MySQL 8.0+ installed and running
- [ ] Backend database created
- [ ] Backend `application.properties` configured
- [ ] Frontend `.env.local` created
- [ ] Backend running on port 8080
- [ ] Frontend running on port 3000
- [ ] Can access `http://localhost:3000`
- [ ] No console errors in browser
- [ ] Backend health check responds
- [ ] Database tables created

---

## 🚀 Next Steps

1. **Initial Data**: Create test users and projects
2. **Authentication**: Test login with credentials
3. **Role Testing**: Verify different role dashboards
4. **API Testing**: Use Postman or similar tool
5. **Deployment**: Deploy to staging environment

---

## 📞 Need Help?

- Check existing GitHub issues
- Review application logs
- Contact development team
- Refer to project documentation

---

**Last Updated**: November 11, 2025
**Version**: 1.0.0
