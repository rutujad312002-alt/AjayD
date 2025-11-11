import { type UserRole } from '@/store/auth';
import { useHasRole } from '@/hooks/use-role';

interface RoleGuardProps {
  children: React.ReactNode;
  roles: UserRole | UserRole[];
  fallback?: React.ReactNode;
}

export default function RoleGuard({
  children,
  roles,
  fallback = null,
}: RoleGuardProps) {
  const hasAccess = useHasRole(roles);

  if (!hasAccess) {
    return fallback;
  }

  return <>{children}</>;
}