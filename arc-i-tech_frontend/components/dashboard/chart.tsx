'use client';

import { Line, Bar, Doughnut } from 'react-chartjs-2';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { cn } from "@/lib/utils";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  Filler
} from 'chart.js';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  BarElement,
  ArcElement,
  Title,
  Tooltip,
  Legend,
  Filler
);

interface ChartData {
  labels: string[];
  datasets: any[];
}

interface ChartProps {
  type: 'line' | 'bar' | 'doughnut';
  data: ChartData;
  title: string;
  description?: string;
  className?: string;
  height?: number;
}

const defaultOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      position: 'top' as const,
    },
  },
};

export function Chart({
  type,
  data,
  title,
  description,
  className,
  height = 350
}: ChartProps) {
  const renderChart = () => {
    switch (type) {
      case 'line':
        return <Line data={data} options={defaultOptions} height={height} />;
      case 'bar':
        return <Bar data={data} options={defaultOptions} height={height} />;
      case 'doughnut':
        return <Doughnut data={data} options={defaultOptions} height={height} />;
      default:
        return null;
    }
  };

  return (
    <Card className={cn("", className)}>
      <CardHeader>
        <CardTitle>{title}</CardTitle>
        {description && <CardDescription>{description}</CardDescription>}
      </CardHeader>
      <CardContent>{renderChart()}</CardContent>
    </Card>
  );
}