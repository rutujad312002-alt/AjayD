# Architecture & Development Guide

Complete technical documentation for developers working on Arc-i-Tech Dashboard System.

## 📋 Table of Contents

- [System Architecture](#system-architecture)
- [Design Patterns](#design-patterns)
- [Backend Architecture](#backend-architecture)
- [Frontend Architecture](#frontend-architecture)
- [Database Schema](#database-schema)
- [Development Standards](#development-standards)
- [Component Guidelines](#component-guidelines)
- [Testing Strategy](#testing-strategy)

## 🏗️ System Architecture

### High-Level Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT LAYER (Next.js)                   │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │  Dashboard   │  │  Auth Pages  │  │  Components  │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
└────────────────────────────┬────────────────────────────────────┘
                             │ HTTP/REST
                             ↓
┌─────────────────────────────────────────────────────────────────┐
│                  API GATEWAY & SECURITY LAYER                   │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │    CORS      │  │  JWT Auth    │  │  Rate Limit  │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ↓
┌─────────────────────────────────────────────────────────────────┐
│               CONTROLLER LAYER (Spring REST)                    │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │  Dashboard   │  │  Team Ctrl   │  │  Project     │          │
│  │  Controller  │  │  Controller  │  │  Controller  │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ↓
┌─────────────────────────────────────────────────────────────────┐
│               SERVICE LAYER (Business Logic)                    │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │  Dashboard   │  │  Team        │  │  Project     │          │
│  │  Service     │  │  Service     │  │  Service     │          │
│  │  (Interface) │  │  (Interface) │  │  (Interface) │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
│         ↓                  ↓                  ↓                  │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │ Role-based   │  │ Role-based   │  │ Role-based   │          │
│  │ Impl (5x)    │  │ Impl (5x)    │  │ Impl (5x)    │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ↓
┌─────────────────────────────────────────────────────────────────┐
│               REPOSITORY LAYER (Data Access)                    │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │  User Repo   │  │  Team Repo   │  │  Project     │          │
│  │  (JPA)       │  │  (JPA)       │  │  Repo (JPA)  │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ↓
┌─────────────────────────────────────────────────────────────────┐
│                    DATABASE LAYER (MySQL)                       │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │  users       │  │  teams       │  │  projects    │          │
│  │  table       │  │  table       │  │  table       │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
└─────────────────────────────────────────────────────────────────┘
```

### Layered Architecture Benefits

- **Separation of Concerns**: Each layer has specific responsibility
- **Maintainability**: Easy to locate and update code
- **Scalability**: Can scale individual layers independently
- **Testability**: Each layer can be tested in isolation
- **Reusability**: Services can be reused across controllers

---

## 🎯 Design Patterns

### 1. Service Pattern

**Purpose**: Encapsulate business logic

```java
// Interface
public interface DashboardService<T> {
    T getDashboardData(String userId);
    // Other methods
}

// Implementation
@Service
@RequiredArgsConstructor
public class SuperAdminDashboardServiceImpl implements DashboardService<SuperAdminDashboardDTO> {
    private final UserRepository userRepository;
    
    @Override
    public SuperAdminDashboardDTO getDashboardData(String userId) {
        // Business logic here
    }
}
```

### 2. Repository Pattern

**Purpose**: Abstract data access logic

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
}
```

### 3. DTO Pattern (Data Transfer Object)

**Purpose**: Transfer data between layers

```java
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDashboardDTO {
    private Long totalUsers;
    private Long activeProjects;
    private List<ProjectDTO> projects;
}
```

### 4. Dependency Injection

**Purpose**: Loose coupling and testability

```java
@Service
@RequiredArgsConstructor  // Generates constructor injection
public class MyService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
}
```

### 5. Strategy Pattern (Role-Based Services)

**Purpose**: Different implementations for different roles

```
DashboardService (Interface)
    ├── SuperAdminDashboardServiceImpl
    ├── SubAdminDashboardServiceImpl
    ├── ProjectDeveloperDashboardServiceImpl
    ├── TeamMemberDashboardServiceImpl
    └── UserDashboardServiceImpl
```

---

## 🔌 Backend Architecture

### Directory Structure

```
Arc-i-Tech_Backend/src/main/java/com/arcitech/
├── controller/
│   ├── SuperAdminDashboardController
│   ├── SubAdminDashboardController
│   ├── ProjectDeveloperDashboardController
│   ├── TeamMemberDashboardController
│   └── UserDashboardController
├── service/
│   ├── dashboard/
│   │   ├── DashboardService (interface)
│   │   ├── SuperAdminDashboardService
│   │   ├── SubAdminDashboardService
│   │   ├── ProjectDeveloperDashboardService
│   │   ├── TeamMemberDashboardService
│   │   └── UserDashboardService
│   └── impl/
│       ├── SuperAdminDashboardServiceImpl
│       ├── SubAdminDashboardServiceImpl
│       ├── ProjectDeveloperDashboardServiceImpl
│       ├── TeamMemberDashboardServiceImpl
│       └── UserDashboardServiceImpl
├── repository/
│   ├── UserRepository
│   ├── TeamRepository
│   ├── ProjectRepository
│   ├── CertificateRepository
│   └── (Other repositories)
├── model/
│   ├── User
│   ├── Team
│   ├── Project
│   ├── Certificate
│   └── (Other entities)
├── dto/
│   ├── dashboard/
│   │   ├── SuperAdminDashboardDTO
│   │   ├── SubAdminDashboardDTO
│   │   ├── ProjectDeveloperDashboardDTO
│   │   ├── TeamMemberDashboardDTO
│   │   ├── UserDashboardDTO
│   │   └── (Other DTOs - 30+)
│   └── (Other DTO folders)
├── security/
│   ├── JwtTokenProvider
│   ├── JwtAuthenticationFilter
│   └── SecurityConfig
└── config/
    ├── ApplicationConfig
    └── CorsConfig
```

### Controller Implementation Pattern

```java
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SuperAdminDashboardController {
    
    private final SuperAdminDashboardService dashboardService;
    
    @GetMapping("/super-admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<SuperAdminDashboardDTO> getDashboard(
        @RequestParam String userId) {
        SuperAdminDashboardDTO dashboard = dashboardService.getDashboardData(userId);
        return ResponseEntity.ok(dashboard);
    }
}
```

### Service Implementation Pattern

```java
@Service
@RequiredArgsConstructor
@Transactional
public class SuperAdminDashboardServiceImpl implements SuperAdminDashboardService {
    
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    
    @Override
    @Transactional(readOnly = true)
    public SuperAdminDashboardDTO getDashboardData(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        long totalUsers = userRepository.count();
        long totalProjects = projectRepository.count();
        
        return SuperAdminDashboardDTO.builder()
            .totalUsers(totalUsers)
            .totalProjects(totalProjects)
            .build();
    }
}
```

---

## 🎨 Frontend Architecture

### Directory Structure

```
arc-i-tech_frontend/
├── app/
│   ├── layout.tsx              # Root layout wrapper
│   ├── page.tsx                # Home/landing page
│   ├── (auth)/                 # Auth route group
│   │   ├── login/
│   │   └── register/
│   ├── (dashboard)/            # Dashboard route group
│   │   ├── super-admin/
│   │   ├── sub-admin/
│   │   ├── project-developer/
│   │   ├── team-member/
│   │   └── user/
│   └── globals.css
├── components/
│   ├── ui/                     # shadcn/ui components
│   │   ├── Button.tsx
│   │   ├── Card.tsx
│   │   ├── Dialog.tsx
│   │   └── (Other base UI components)
│   ├── dashboard/              # Dashboard-specific components
│   │   ├── DashboardHeader.tsx
│   │   ├── StatCard.tsx
│   │   ├── ActivityFeed.tsx
│   │   └── (Role-specific components)
│   ├── layout/                 # Layout components
│   │   ├── Sidebar.tsx
│   │   ├── Navbar.tsx
│   │   └── MainLayout.tsx
│   └── common/                 # Shared components
│       ├── Loading.tsx
│       ├── ErrorBoundary.tsx
│       └── (Other common components)
├── store/                      # Zustand state management
│   ├── useAuthStore.ts         # Authentication state
│   ├── useDashboardStore.ts    # Dashboard state
│   └── (Other stores)
├── hooks/                      # Custom React hooks
│   ├── useAuth.ts
│   ├── useFetch.ts
│   └── (Other custom hooks)
├── lib/
│   ├── api.ts                  # API client setup
│   ├── utils.ts                # Utility functions
│   └── constants.ts            # Constants
├── types/                      # TypeScript types/interfaces
│   ├── index.ts
│   └── (Other type definitions)
├── public/                     # Static assets
├── package.json
├── tsconfig.json
├── next.config.ts
└── tailwind.config.js
```

### Component Architecture Pattern

```typescript
// pages/dashboard/super-admin/page.tsx
import SuperAdminLayout from '@/components/dashboard/layouts/SuperAdminLayout';
import StatisticsCard from '@/components/dashboard/StatisticsCard';
import ActivityFeed from '@/components/dashboard/ActivityFeed';

export default function SuperAdminDashboard() {
  const { user } = useAuth();
  
  return (
    <SuperAdminLayout>
      <div className="grid grid-cols-4 gap-4">
        <StatisticsCard title="Total Users" value={150} />
        <StatisticsCard title="Total Projects" value={45} />
        <StatisticsCard title="Active Teams" value={12} />
        <StatisticsCard title="System Health" value="98.5%" />
      </div>
      <ActivityFeed />
    </SuperAdminLayout>
  );
}
```

### State Management (Zustand)

```typescript
// store/useAuthStore.ts
import { create } from 'zustand';

interface AuthState {
  user: User | null;
  token: string | null;
  isLoading: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  user: null,
  token: null,
  isLoading: false,
  
  login: async (email, password) => {
    set({ isLoading: true });
    try {
      const response = await api.post('/auth/login', { email, password });
      set({ user: response.data.user, token: response.data.token });
    } finally {
      set({ isLoading: false });
    }
  },
  
  logout: () => {
    set({ user: null, token: null });
  },
}));
```

---

## 📊 Database Schema

### Core Entity Relationships

```
User (1) ──────→ (n) Team
   ↓
   └── (Team Membership)
   
Team (1) ──────→ (n) Project
   ↓
   └── (Project Assignment)
   
Project (1) ──────→ (n) Task
   ↓
   └── (Task Progress)
   
User (1) ──────→ (n) Certificate
   ↓
   └── (Certification Records)
```

### Key Tables

#### users
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  name VARCHAR(255),
  role_id BIGINT,
  active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (role_id) REFERENCES roles(id)
);
```

#### teams
```sql
CREATE TABLE teams (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  team_type VARCHAR(100),
  created_by BIGINT,
  active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (created_by) REFERENCES users(id)
);
```

#### projects
```sql
CREATE TABLE projects (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  status VARCHAR(50),
  progress DOUBLE DEFAULT 0.0,
  team_id BIGINT,
  client_id BIGINT,
  start_date DATE,
  end_date DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (team_id) REFERENCES teams(id),
  FOREIGN KEY (client_id) REFERENCES users(id)
);
```

---

## 📋 Development Standards

### Code Style Guide

#### Java Conventions

```java
// Class naming: PascalCase
public class UserDashboardService {}

// Method naming: camelCase
public void getUserProjects() {}

// Constant naming: UPPER_CASE
public static final String APP_NAME = "Arc-i-Tech";

// Private fields with underscore prefix (optional)
private String _internalState;

// Annotations on separate lines
@Service
@RequiredArgsConstructor
@Transactional
public class MyService {}
```

#### TypeScript/React Conventions

```typescript
// Component naming: PascalCase
const DashboardCard: FC<Props> = () => {
  return <div>Component</div>;
};

// Hook naming: useXyz
const useUserData = () => {
  const [data, setData] = useState(null);
  return data;
};

// Variable naming: camelCase
const maxRetries = 3;
const isLoading = false;

// Constant naming: UPPER_CASE
const API_TIMEOUT = 30000;
```

### Commenting Standards

```java
/**
 * Get dashboard data for super admin users.
 * 
 * This method retrieves comprehensive system analytics including
 * total users, projects, teams and system health metrics.
 *
 * @param userId the ID of the super admin user
 * @return SuperAdminDashboardDTO containing dashboard data
 * @throws RuntimeException if user not found
 * @since 1.0.0
 */
public SuperAdminDashboardDTO getDashboardData(String userId) {
    // Implementation
}
```

### Error Handling

#### Backend
```java
try {
    // Try to fetch user
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
} catch (ResourceNotFoundException e) {
    logger.error("User lookup failed: {}", e.getMessage());
    throw new ServiceException("Cannot retrieve user data", e);
}
```

#### Frontend
```typescript
try {
  const response = await api.get('/dashboard/super-admin');
  setDashboard(response.data);
} catch (error) {
  if (axios.isAxiosError(error)) {
    if (error.response?.status === 403) {
      router.push('/unauthorized');
    } else {
      setError(error.response?.data?.message || 'An error occurred');
    }
  }
}
```

### Logging Standards

```java
// Backend logging levels
logger.debug("Detailed diagnostic information");      // Development
logger.info("General informational messages");         // Important events
logger.warn("Warning messages for issues");            // Potential problems
logger.error("Error messages with stack traces");      // Errors
```

---

## 🧩 Component Guidelines

### Creating a New Service

1. **Create Interface** in `service/dashboard/`:
```java
public interface NewService {
    DTO getNewData(String userId);
    void performAction(String id, ActionDTO action);
}
```

2. **Create Implementation** in `service/impl/`:
```java
@Service
@RequiredArgsConstructor
public class NewServiceImpl implements NewService {
    private final NewRepository repository;
    
    @Override
    public DTO getNewData(String userId) {
        // Implementation
    }
}
```

3. **Create Controller** in `controller/`:
```java
@RestController
@RequestMapping("/api/v1/new")
@RequiredArgsConstructor
public class NewController {
    private final NewService service;
    
    @GetMapping
    public ResponseEntity<DTO> getData() {
        return ResponseEntity.ok(service.getNewData(id));
    }
}
```

### Creating a New Frontend Component

1. **Create Component** in `components/`:
```typescript
interface Props {
  title: string;
  data: DataType;
  onAction?: () => void;
}

const NewComponent: FC<Props> = ({ title, data, onAction }) => {
  return (
    <div className="component">
      <h1>{title}</h1>
      {/* Component content */}
    </div>
  );
};

export default NewComponent;
```

2. **Create Hook** if needed in `hooks/`:
```typescript
export const useNewData = () => {
  const [data, setData] = useState<DataType | null>(null);
  const [loading, setLoading] = useState(false);
  
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      const result = await api.get('/endpoint');
      setData(result.data);
      setLoading(false);
    };
    
    fetchData();
  }, []);
  
  return { data, loading };
};
```

---

## 🧪 Testing Strategy

### Backend Testing

#### Unit Tests
```java
@SpringBootTest
class UserDashboardServiceImplTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserDashboardServiceImpl service;
    
    @Test
    void testGetDashboardData_Success() {
        // Given
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        // When
        UserDashboardDTO dashboard = service.getDashboardData("1");
        
        // Then
        assertNotNull(dashboard);
        verify(userRepository, times(1)).findById(1L);
    }
}
```

#### Integration Tests
```java
@SpringBootTest
@AutoConfigureMockMvc
class UserDashboardControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testGetDashboard_WithValidToken() throws Exception {
        mockMvc.perform(get("/api/v1/dashboard/user")
            .header("Authorization", "Bearer " + validToken))
            .andExpect(status().isOk());
    }
}
```

### Frontend Testing

#### Component Tests
```typescript
import { render, screen } from '@testing-library/react';
import StatCard from '@/components/dashboard/StatCard';

describe('StatCard', () => {
  it('renders title and value', () => {
    render(<StatCard title="Users" value={150} />);
    expect(screen.getByText('Users')).toBeInTheDocument();
    expect(screen.getByText('150')).toBeInTheDocument();
  });
});
```

---

## 📈 Performance Optimization

### Backend

```java
// Use pagination for large datasets
@GetMapping("/projects")
public Page<ProjectDTO> getProjects(Pageable pageable) {
    return projectRepository.findAll(pageable);
}

// Use caching for frequently accessed data
@Cacheable(value = "users", key = "#id")
public User getUserById(Long id) {
    return userRepository.findById(id).orElse(null);
}

// Use @Transactional(readOnly = true) for read operations
@Transactional(readOnly = true)
public List<ProjectDTO> getProjects() {
    // Optimized for read-only operations
}
```

### Frontend

```typescript
// Use React.memo for component memoization
export default memo(StatCard);

// Use useMemo for expensive computations
const memoizedValue = useMemo(() => {
  return expensiveCalculation(data);
}, [data]);

// Use useCallback for memoized callbacks
const handleClick = useCallback(() => {
  processData();
}, []);
```

---

**Last Updated**: November 11, 2025
**Version**: 1.0.0
