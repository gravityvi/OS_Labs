//Author: Ravi Sawlani
//Id: 201601120

import java.util.*;
import java.io.*;

public class test
{
    static class Node implements Comparable<Node>{
        int id;
        int arrival_time;
        int burst_time;
    
        Node(int id,int arrival_time, int burst_time)
        {
            this.id = id;
            this.arrival_time = arrival_time;
            this.burst_time = burst_time;
            

        }

    public int compareTo(Node n)
        {
            return Long.compare(this.arrival_time, n.arrival_time);
        }
    }

    static class burstTime implements Comparator<Node>
    {
        @Override
        public int compare(Node o1, Node o2) {
            return Integer.compare(o1.burst_time, o2.burst_time);
        }
    }
    static class arrivalTime implements Comparator<Node>
    {
        @Override
        public int compare(Node o1, Node o2) {
            return Integer.compare(o1.arrival_time,o2.arrival_time);
        }
    }
    public static void main(String[] args) throws Exception {
            String fileName = args[0];
            File file = new File(args[0]);
            Scanner sc = new Scanner(file);
            Node arr[] = new Node[20];

            int cnt=0;
            while(sc.hasNextLine())
            {
                String str[] = sc.nextLine().split(" ");
                int id = Integer.parseInt(str[0]);
                int arrival_time = Integer.parseInt(str[1]);
                int burst_time = Integer.parseInt(str[2]);
                arr[cnt] = new Node(id, arrival_time, burst_time);
                cnt++;
            }
            arr = Arrays.copyOfRange(arr, 0, cnt);
            Arrays.sort(arr, new arrivalTime());
            String type = args[1];

            if(type.equals("FCFS"))
            {
                 /* code for FCFS */
                 int clk=0;
                // Arrays.sort(arr);
                int res[] = new int[cnt];
                int fin[] = new int[cnt];
                int run[] = new int[cnt];
                clk=arr[0].arrival_time;
                for(int i=0;i<cnt;i++)
                {
                    for(int j=0;j<arr[i].burst_time;j++)
                    {
                        if(j==0)
                        {
                            res[i]=clk;
                        }
                        System.out.println("<System time " +clk+"> process "+arr[i].id+" is running");
                        run[i]++;
                        clk++;
                    }
                    fin[i]=clk;
                    System.out.println("<System time " +clk+"> process "+arr[i].id+" has finished ......");
                }

                double responseTime=0;
                for(int i=0;i<cnt;i++)
                {
                    responseTime+=((double)res[i]-arr[i].arrival_time);
                }
                responseTime = responseTime/(double)cnt;
                

                double turnAround=0;
                for(int i=0;i<cnt;i++)
                {
                    turnAround+=((double)(fin[i]-arr[i].arrival_time));

                }
                turnAround=turnAround/cnt;
                

                double waitTime=0;
                for(int i=0;i<cnt;i++)
                {
                    waitTime+=((double)fin[i]-run[i]-arr[i].arrival_time);
                }
                waitTime=waitTime/(double)(cnt);
                
                System.out.println("=====================================================================");
                System.out.println("Average waiting time "+waitTime);
                System.out.println("Average response time "+responseTime);
                System.out.println("Average turnaround time "+turnAround);
                System.out.println("=====================================================================");

            }
            else if(type.equals("SJF"))
            {
                    /* code SJF */
                    boolean start[] = new boolean[cnt];
                    int res[] = new int[cnt];
                    int run[] = new int[cnt];
                    int fin[] = new int[cnt];
                    int clk=0;
                    int finished=0;
                    ArrayList<Node> al = new ArrayList<Node>();
                    while(finished<cnt)
                    {
                        for(int i=0;i<cnt;i++)
                        {
                            if(arr[i].arrival_time==clk)
                            {
                                al.add(new Node(arr[i].id,arr[i].arrival_time,arr[i].burst_time));
                                
                            }
                        }
                        Collections.sort(al,new burstTime());
                        if(al.isEmpty())
                        {
                            continue;
                        }
                        if(al.get(0).burst_time==0)
                        {
                            System.out.println("<System time " +clk+"> process "+al.get(0).id+" has finished.....");
                            fin[al.get(0).id-1]=clk;
                            al.remove(0);
                            finished++;
                            clk--;
                        }
                        else
                        {
                            if(!start[al.get(0).id-1])
                            {
                                res[al.get(0).id-1]=clk;
                                start[al.get(0).id-1]=true;
                            }
                            System.out.println("<System time " +clk+"> process "+al.get(0).id+" is running");
                            al.get(0).burst_time=al.get(0).burst_time-1;
                            run[al.get(0).id-1]++;
                            
                            
                        }

                        clk++;

                    }

                    double responseTime=0;
                    double waitTime=0;
                    double turnAround=0;
                    for(int i=0;i<cnt;i++)
                    {
                        responseTime+=((double)res[arr[i].id-1]-arr[i].arrival_time);
                        waitTime+=((double)fin[arr[i].id-1]-arr[i].arrival_time-run[arr[i].id-1]);
                        turnAround+=((double)fin[arr[i].id-1]-arr[i].arrival_time);
                        
                    }
                    waitTime/=((double)cnt);
                    turnAround/=((double)cnt);
                    responseTime=responseTime/cnt;
                    System.out.println("=====================================================================");
                    System.out.println("Average waiting time "+waitTime);
                    System.out.println("Average response time "+responseTime);
                    System.out.println("Average turnaround time "+turnAround);
                    System.out.println("=====================================================================");                    
                }
            else
            {
                /* code for round robin*/
                int fin[] = new int[cnt];
                int res[] = new int[cnt];
                int run[] = new int[cnt];
                boolean started[] = new boolean[cnt];
                int time_quantum=Integer.parseInt(args[2]);
                Queue<Node> q= new LinkedList<Node>();
                int clk=0;
                boolean jobs[] = new boolean[cnt];
                int timer=time_quantum;
                int dam=0;
                int start=0;
                int size=0;
                boolean flag =true;
                int finished=0;
                while(finished<cnt)
                {
                while(q.isEmpty())
                {
                    
                    for(int i=0;i<cnt;i++)
                    {
                        if(arr[i].arrival_time<=clk && !jobs[i])
                        {
                                q.add(arr[i]);
                                jobs[i]=true;
                                clk--;
                        }
                    }
                    clk++;
                }
                

                Node n1 = q.remove();
                // System.out.println("n1.id "+n1.id);
                int temp=Integer.min(time_quantum,n1.burst_time);
                for(int i=0;i<temp;i++)
                {
                        System.out.println("<System time "+clk+"> process "+n1.id+" is running");
                        if(!started[n1.id-1])
                        {
                            res[n1.id-1]=clk;
                            started[n1.id-1]=true;
                        }
                        run[n1.id-1]++;
                        n1.burst_time=n1.burst_time-1;
                        clk++;
                        for(int j=0;j<cnt;j++)
                        {
                            if(arr[j].arrival_time<clk+1 &&!jobs[j])
                            {
                                q.add(arr[j]);
                                jobs[j]=true;
                            }
                        }
                }
                if(n1.burst_time>0)
                {
                    q.add(n1);
                }
                else
                {
                    System.out.println("<System time "+clk+"> process "+n1.id+" has finished.....");
                    fin[n1.id-1]=clk;
                    finished++;
                }



            }

            double waitTime=0;
            double turnAround=0;
            double responseTime=0;
            for(int i=0;i<cnt;i++)
            {
                responseTime+=((double)res[arr[i].id-1]-arr[i].arrival_time);
                waitTime+=((double)fin[arr[i].id-1]-arr[i].arrival_time-run[arr[i].id-1]);
                turnAround+=((double)fin[arr[i].id-1]-arr[i].arrival_time);
            }   
            responseTime/=(double)cnt;
            waitTime/=(double)cnt;
            turnAround/=(double)cnt;
            System.out.println("=====================================================================");
            System.out.println("Average waiting time "+waitTime);
            System.out.println("Average response time "+responseTime);
            System.out.println("Average turnaround time "+turnAround);
            System.out.println("=====================================================================");                   
           



            
               
              
               


            }
            


    }
}