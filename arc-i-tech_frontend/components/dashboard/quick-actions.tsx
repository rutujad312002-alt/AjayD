'use client';

import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { cn } from "@/lib/utils";

type LucideIcon = React.ComponentType<{ className?: string }>;

export interface QuickAction {
  icon: LucideIcon;
  label: string;
  description?: string;
  onClick: () => void;
  variant?: 'default' | 'secondary' | 'destructive' | 'outline';
}

interface QuickActionsProps {
  actions: QuickAction[];
  className?: string;
}

export function QuickActions({ actions, className }: QuickActionsProps) {
  return (
    <Card className={cn("p-4", className)}>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        {actions.map((action, index) => {
          const Icon = action.icon;
          return (
            <Button
              key={index}
              variant={action.variant || "outline"}
              className="flex h-24 flex-col items-center justify-center gap-2"
              onClick={action.onClick}
            >
              <Icon className="h-5 w-5" />
              <div className="text-xs font-medium">{action.label}</div>
              {action.description && (
                <div className="text-xs text-muted-foreground">
                  {action.description}
                </div>
              )}
            </Button>
          );
        })}
      </div>
    </Card>
  );
}