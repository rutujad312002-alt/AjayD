import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuthStore, type UserRole } from '@/store/auth';

export function useRole() {
  const { user } = useAuthStore();
  return user?.role || null;
}

export function useHasRole(roles: UserRole | UserRole[]) {
  const userRole = useRole();
  if (!userRole) return false;
  
  const allowedRoles = Array.isArray(roles) ? roles : [roles];
  return allowedRoles.includes(userRole);
}

export function useRequireRole(roles: UserRole | UserRole[]) {
  const hasRole = useHasRole(roles);
  const { push } = useRouter();
  
  useEffect(() => {
    if (!hasRole) {
      push('/unauthorized');
    }
  }, [hasRole, push]);
  
  return hasRole;
}