import { cn } from "@/lib/utils";

interface DashboardLayoutProps {
  children: React.ReactNode;
  className?: string;
}

export function DashboardLayout({
  children,
  className,
}: DashboardLayoutProps) {
  return (
    <div className={cn("p-6 space-y-6", className)}>
      {children}
    </div>
  );
}