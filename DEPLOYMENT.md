# Deployment & Operations Guide

Complete guide for deploying and managing Arc-i-Tech Dashboard System in production.

## 📋 Table of Contents

- [Deployment Prerequisites](#deployment-prerequisites)
- [Deployment Strategies](#deployment-strategies)
- [Backend Deployment](#backend-deployment)
- [Frontend Deployment](#frontend-deployment)
- [Database Migration](#database-migration)
- [Monitoring & Logging](#monitoring--logging)
- [Backup & Recovery](#backup--recovery)
- [Performance Tuning](#performance-tuning)
- [Troubleshooting](#troubleshooting)
- [Maintenance Schedule](#maintenance-schedule)

## 📋 Deployment Prerequisites

### Infrastructure Requirements

#### Server Specifications
- **CPU**: 4+ cores (recommended 8+ for production)
- **RAM**: 16GB minimum (32GB recommended)
- **Storage**: 100GB SSD (expandable)
- **Network**: 1Gbps internet connection
- **Uptime**: 99.9% guaranteed

#### Production Services
- **Web Server**: Nginx or Apache
- **Application Server**: Tomcat (Spring Boot embedded)
- **Database**: MySQL 8.0+ (managed or self-hosted)
- **Cache**: Redis (optional, for caching)
- **Message Queue**: RabbitMQ (optional, for async tasks)

### DNS & Domain Setup

```
arc-i-tech.com           → Frontend (Next.js)
api.arc-i-tech.com       → Backend (Spring Boot)
admin.arc-i-tech.com     → Admin Panel (optional)
```

### SSL/TLS Certificates

```bash
# Using Let's Encrypt for free SSL certificates
sudo certbot certonly --standalone -d arc-i-tech.com -d api.arc-i-tech.com
```

### CI/CD Pipeline Setup

- **Git Repository**: GitHub/GitLab
- **CI/CD Service**: GitHub Actions, GitLab CI, or Jenkins
- **Container Registry**: Docker Hub or private registry
- **Artifact Repository**: Nexus or Artifactory

---

## 🚀 Deployment Strategies

### 1. Docker Containerization (Recommended)

#### Backend Dockerfile

```dockerfile
# Stage 1: Build
FROM maven:3.8.1-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/Arc-i-Tech_Backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Frontend Dockerfile

```dockerfile
# Stage 1: Build
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# Stage 2: Runtime
FROM node:18-alpine
WORKDIR /app
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/package*.json ./
COPY public ./public

EXPOSE 3000
CMD ["npm", "start"]
```

#### Docker Compose (for staging/local)

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: arc-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root_password
      MYSQL_DATABASE: arc_i_tech
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

  backend:
    build: ./Arc-i-Tech_Backend
    container_name: arc-backend
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/arc_i_tech
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root_password
    ports:
      - "8080:8080"
    depends_on:
      mysql:
        condition: service_healthy
    restart: unless-stopped

  frontend:
    build: ./arc-i-tech_frontend
    container_name: arc-frontend
    environment:
      NEXT_PUBLIC_API_BASE_URL: http://backend:8080/api/v1
    ports:
      - "3000:3000"
    depends_on:
      - backend
    restart: unless-stopped

volumes:
  mysql_data:
```

### 2. Traditional Deployment

Deploy on VPS or dedicated servers without containers.

---

## 🔧 Backend Deployment

### Step 1: Prepare Backend JAR

```bash
cd Arc-i-Tech_Backend
mvn clean package -DskipTests -P production
```

### Step 2: Configure Production Properties

Create `application-production.properties`:

```properties
# Server
server.port=8080
server.compression.enabled=true
server.compression.min-response-size=1024

# Database
spring.datasource.url=jdbc:mysql://db-server:3306/arc_i_tech?useSSL=true&serverTimezone=UTC
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.jpa.hibernate.ddl-auto=validate

# JWT
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000

# Logging
logging.level.root=WARN
logging.level.com.arcitech=INFO
logging.file.name=/var/log/arc-backend.log
logging.file.max-size=10MB
logging.file.max-history=30

# Security
server.ssl.key-store=${SSL_KEYSTORE_PATH}
server.ssl.key-store-password=${SSL_KEYSTORE_PASSWORD}
server.ssl.key-store-type=PKCS12

# CORS
cors.allowed-origins=https://arc-i-tech.com
```

### Step 3: Create Systemd Service

Create `/etc/systemd/system/arc-backend.service`:

```ini
[Unit]
Description=Arc-i-Tech Backend Service
After=network.target

[Service]
Type=simple
User=arc-app
WorkingDirectory=/opt/arc-backend
ExecStart=/usr/bin/java -jar Arc-i-Tech_Backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=production
Restart=on-failure
RestartSec=5s
StandardOutput=journal
StandardError=journal

[Install]
WantedBy=multi-user.target
```

### Step 4: Deploy & Start Service

```bash
# Copy JAR to server
scp Arc-i-Tech_Backend-0.0.1-SNAPSHOT.jar user@server:/opt/arc-backend/

# Enable and start service
sudo systemctl daemon-reload
sudo systemctl enable arc-backend
sudo systemctl start arc-backend
sudo systemctl status arc-backend

# Check logs
sudo journalctl -u arc-backend -f
```

### Step 5: Configure Nginx Reverse Proxy

Create `/etc/nginx/sites-available/arc-api`:

```nginx
upstream arc_backend {
    server localhost:8080;
}

server {
    listen 443 ssl http2;
    listen [::]:443 ssl http2;
    server_name api.arc-i-tech.com;

    ssl_certificate /etc/letsencrypt/live/arc-i-tech.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/arc-i-tech.com/privkey.pem;
    
    # SSL configuration
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers on;

    # Logging
    access_log /var/log/nginx/arc-api-access.log;
    error_log /var/log/nginx/arc-api-error.log;

    # Proxy settings
    location /api/v1 {
        proxy_pass http://arc_backend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Connection "";
        
        # Timeouts
        proxy_connect_timeout 60s;
        proxy_send_timeout 60s;
        proxy_read_timeout 60s;
    }
}

# Redirect HTTP to HTTPS
server {
    listen 80;
    listen [::]:80;
    server_name api.arc-i-tech.com;
    
    return 301 https://$server_name$request_uri;
}
```

Enable and test:

```bash
sudo ln -s /etc/nginx/sites-available/arc-api /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

---

## 🎨 Frontend Deployment

### Step 1: Build Frontend

```bash
cd arc-i-tech_frontend
npm run build
```

### Step 2: Configure Environment

Create `.env.production`:

```env
NEXT_PUBLIC_API_BASE_URL=https://api.arc-i-tech.com/api/v1
NEXT_PUBLIC_APP_NAME=Arc-i-Tech Dashboard
NEXT_PUBLIC_ENABLE_ANALYTICS=true
```

### Step 3: Configure Nginx for Frontend

Create `/etc/nginx/sites-available/arc-frontend`:

```nginx
upstream arc_frontend {
    server localhost:3000;
}

server {
    listen 443 ssl http2;
    listen [::]:443 ssl http2;
    server_name arc-i-tech.com;

    ssl_certificate /etc/letsencrypt/live/arc-i-tech.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/arc-i-tech.com/privkey.pem;
    
    # Cache settings
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg|woff|woff2|ttf|eot)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }

    # Next.js pages
    location / {
        proxy_pass http://arc_frontend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    # _next folder
    location /_next {
        proxy_pass http://arc_frontend;
        proxy_cache_valid 200 60m;
        add_header Cache-Control "public, max-age=3600";
    }
}

# Redirect HTTP to HTTPS
server {
    listen 80;
    listen [::]:80;
    server_name arc-i-tech.com;
    
    return 301 https://$server_name$request_uri;
}
```

### Step 4: Create PM2 Configuration

Create `ecosystem.config.js`:

```javascript
module.exports = {
  apps: [{
    name: 'arc-frontend',
    script: 'node_modules/.bin/next',
    args: 'start',
    instances: 'max',
    exec_mode: 'cluster',
    env: {
      NODE_ENV: 'production',
      NEXT_PUBLIC_API_BASE_URL: 'https://api.arc-i-tech.com/api/v1'
    },
    error_file: '/var/log/arc-frontend-error.log',
    out_file: '/var/log/arc-frontend-out.log',
    log_date_format: 'YYYY-MM-DD HH:mm:ss Z'
  }]
};
```

### Step 5: Deploy with PM2

```bash
# Install PM2 globally
npm install -g pm2

# Deploy
pm2 start ecosystem.config.js

# Save PM2 configuration
pm2 save

# Enable PM2 on system startup
pm2 startup
```

---

## 💾 Database Migration

### Step 1: Backup Current Database

```bash
mysqldump -u root -p arc_i_tech > arc_i_tech_backup_$(date +%Y%m%d).sql
```

### Step 2: Update Schema (if needed)

```sql
-- Example migration
ALTER TABLE projects ADD COLUMN updated_progress DOUBLE DEFAULT 0.0;
ALTER TABLE users ADD COLUMN last_login TIMESTAMP NULL;
```

### Step 3: Verify Migration

```bash
mysql -u root -p arc_i_tech < migration_script.sql
```

### Step 4: Post-Migration Testing

```sql
-- Verify table structure
DESC projects;

-- Verify data integrity
SELECT COUNT(*) FROM users;
SELECT COUNT(*) FROM projects;
```

---

## 📊 Monitoring & Logging

### Backend Monitoring

#### Application Health Check

```bash
curl -s http://api.arc-i-tech.com/health | jq
```

#### JVM Monitoring

```bash
# CPU and Memory usage
jps -l
jstat -gc <pid>

# Thread information
jstack <pid> > thread_dump.txt
```

#### Log Monitoring

```bash
# Real-time log monitoring
tail -f /var/log/arc-backend.log

# Search for errors
grep ERROR /var/log/arc-backend.log

# Count errors by type
grep ERROR /var/log/arc-backend.log | cut -d' ' -f5- | sort | uniq -c
```

### Frontend Monitoring

#### Performance Metrics

```bash
# Check Core Web Vitals using Chrome DevTools or tools like:
# - GTmetrix
# - PageSpeed Insights
# - WebPageTest
```

#### Error Tracking

Monitor frontend errors using services like:
- Sentry
- LogRocket
- Rollbar

### Database Monitoring

```bash
# Check connection pool
mysql -u root -p -e "SHOW PROCESSLIST;"

# Check slow queries
mysql -u root -p -e "SHOW VARIABLES LIKE 'slow_query%';"

# Monitor replication (if applicable)
mysql -u root -p -e "SHOW SLAVE STATUS\G"
```

### Set Up Alerts

Configure monitoring for:
- Application availability (uptime)
- Error rate threshold
- Response time degradation
- Database connection issues
- Disk space usage
- Memory usage

---

## 🔄 Backup & Recovery

### Automated Daily Backups

Create `/usr/local/bin/arc-backup.sh`:

```bash
#!/bin/bash

BACKUP_DIR="/backups/arc-i-tech"
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE="$BACKUP_DIR/arc_i_tech_$DATE.sql"

# Create backup
mysqldump -u root -p$DB_PASSWORD arc_i_tech > $BACKUP_FILE

# Compress backup
gzip $BACKUP_FILE

# Keep only last 30 days
find $BACKUP_DIR -type f -mtime +30 -delete

echo "Backup completed: $BACKUP_FILE.gz" >> /var/log/arc-backup.log
```

Schedule with cron:

```bash
# Edit crontab
crontab -e

# Add: Daily backup at 2 AM
0 2 * * * /usr/local/bin/arc-backup.sh
```

### Recovery Procedure

```bash
# List available backups
ls -lh /backups/arc-i-tech/

# Restore from backup
gunzip -c /backups/arc-i-tech/arc_i_tech_20251111_020000.sql.gz | mysql -u root -p arc_i_tech

# Verify restore
mysql -u root -p -e "SELECT COUNT(*) FROM arc_i_tech.users;"
```

---

## ⚙️ Performance Tuning

### Database Optimization

```sql
-- Create indexes for common queries
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_project_status ON projects(status);
CREATE INDEX idx_team_created ON teams(created_at);

-- Enable query cache
SET GLOBAL query_cache_type = 1;
SET GLOBAL query_cache_size = 268435456;

-- Optimize tables
OPTIMIZE TABLE users;
OPTIMIZE TABLE teams;
OPTIMIZE TABLE projects;
```

### Spring Boot Configuration

```properties
# Connection Pool Tuning
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000

# Cache Configuration
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379

# Thread Pool
spring.task.execution.pool.core-size=10
spring.task.execution.pool.max-size=20
spring.task.execution.pool.queue-capacity=100
```

### Nginx Optimization

```nginx
# Gzip compression
gzip on;
gzip_vary on;
gzip_min_length 1000;
gzip_types text/plain text/css text/xml text/javascript 
           application/x-javascript application/xml+rss;

# Connection settings
keepalive_timeout 65;
client_max_body_size 100M;

# Worker processes
worker_processes auto;
worker_connections 1024;
```

---

## 🐛 Troubleshooting

### Common Production Issues

#### 1. High Memory Usage

```bash
# Check memory usage
free -h

# Identify memory-heavy processes
ps aux --sort=-%mem | head

# Restart service
sudo systemctl restart arc-backend
```

#### 2. Database Connection Errors

```bash
# Check MySQL status
sudo systemctl status mysql

# Check connection limit
mysql -u root -p -e "SHOW VARIABLES LIKE 'max_connections';"

# Increase if needed
mysql -u root -p -e "SET GLOBAL max_connections = 1000;"
```

#### 3. SSL Certificate Errors

```bash
# Check certificate expiration
openssl x509 -in /path/to/cert.pem -noout -dates

# Renew certificate
sudo certbot renew

# Restart services
sudo systemctl restart nginx
```

#### 4. Slow API Responses

```bash
# Check slow query log
mysql -u root -p arc_i_tech -e "SET GLOBAL slow_query_log = 'ON';"

# Monitor queries
mysql -u root -p -e "SHOW PROCESSLIST;"

# Analyze query performance
mysql -u root -p -e "EXPLAIN SELECT * FROM projects WHERE status='active';"
```

---

## 📅 Maintenance Schedule

### Daily Tasks
- ✅ Monitor error logs
- ✅ Check system health metrics
- ✅ Verify backup completion
- ✅ Monitor API response times

### Weekly Tasks
- ✅ Review performance metrics
- ✅ Check disk space usage
- ✅ Database integrity checks
- ✅ Security updates review

### Monthly Tasks
- ✅ Database optimization
- ✅ Backup restoration test
- ✅ SSL certificate check
- ✅ Dependency updates

### Quarterly Tasks
- ✅ Major version updates
- ✅ Performance tuning review
- ✅ Disaster recovery drill
- ✅ Security audit

### Annual Tasks
- ✅ Full system upgrade
- ✅ Architecture review
- ✅ Capacity planning
- ✅ Compliance audit

---

## 📞 Support & Escalation

### Escalation Matrix

| Issue Level | Response Time | Action |
|------------|---------------|--------|
| Critical (Down) | 15 minutes | Page on-call engineer |
| High (Degraded) | 1 hour | Notify senior engineer |
| Medium (Minor) | 4 hours | Create ticket |
| Low (Enhancement) | 24 hours | Backlog |

### Emergency Contacts

- **On-Call Engineer**: [Phone Number]
- **DevOps Team**: [Email]
- **Database Admin**: [Email]
- **Security Team**: [Email]

---

**Last Updated**: November 11, 2025
**Version**: 1.0.0
**Maintained By**: DevOps Team
