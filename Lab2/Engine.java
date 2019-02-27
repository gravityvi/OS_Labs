import java.util.*;
import java.io.*;

public class Engine{
    public static double opt(ArrayList<Integer> al, int phyFrame,boolean flag)
    {


       
        

        int miss=0;
        int ref=0;
        int arr[] = new int[phyFrame];
        HashSet<Integer> hs = new HashSet<Integer>();
        Arrays.fill(arr, -1);
        int count=0;
        for(int i=0;i<al.size();i++)
        {
            boolean fault=false;
            if(count<phyFrame)
            {

                if(hs.contains(al.get(i)))
                {
                    //hit
                }
                else
                {
                    //miss
                    arr[count]=al.get(i);
                    hs.add(arr[count]);
                    count++;
                }
                
            }
            else
            {
                ref++;
                if(hs.contains(al.get(i)))
                {
                    //hit
                }
                else
                {
                    //miss
                    fault=true;
                    miss++;
                    ArrayList<Integer> leastUsed = new ArrayList<>();
                    Iterator<Integer> it = hs.iterator();
                    while(it.hasNext())
                    {
                        leastUsed.add(it.next());
                    }
                    for(int j=i+1;j<al.size();j++)
                    {
                        if(leastUsed.size()==1)
                        {
                            break;
                        }
                        leastUsed.remove(al.get(j));
                        
                    }
                    int x = leastUsed.remove(0);
                    for(int j=0;j<arr.length;j++)
                    {
                        if(arr[j]==x)
                        {
                            arr[j]=al.get(i);
                            hs.remove(x);
                            hs.add(al.get(i));
                        }

                    }
                    
                }
                
            }
            if(!flag)
            {
                print(arr,fault);
                
            }
                
        }
        double missRate = (double)miss/(double)(ref);
        missRate*=100;
        System.out.println("opt, "+phyFrame+" frames Miss rate: "+missRate+"%");
        return missRate;
    }

    public static double lru(ArrayList<Integer> al,int phyFrame,boolean flag)
    {
        boolean fault=false;
        int miss=0;
        int ref=0;
        int arr[] = new int[phyFrame];
        Arrays.fill(arr, -1);
        Queue<Integer> q = new LinkedList<Integer>();
        HashSet<Integer> hs  = new HashSet<Integer>();
        int count=0;
        for(int i=0;i<al.size();i++)
        {
            fault = false;
            if(count<phyFrame)
            {
                if(hs.contains(al.get(i)))
                {
                    //hit
                }
                else
                {
                    //miss
                    hs.add(al.get(i));
                    q.add(al.get(i));
                    count++;

                }
            }
            else
            {
                fault=true;
                ref++;
                if(hs.contains(al.get(i)))
                {
                    //hit
                    q.remove(al.get(i));
                    q.add(al.get(i));
                }
                else
                {
                    //miss
                    miss++;
                    int x = q.poll();
                    hs.remove(x);
                    q.add(al.get(i));
                    hs.add(al.get(i));
                    
                }
            }
            
            for(int j=0;j<phyFrame;j++)
            {
                if(q.isEmpty())
                {
                    break;
                }
                arr[j]=q.remove();
               
            }
            if(!flag)
                print(arr,fault);
            for(int j=0;j<phyFrame;j++)
            {
                if(arr[j]==-1)
                {
                    break;
                }
                q.add(arr[j]);
            }


        }
        
        double missRate = (double)miss/(double)(ref);
        missRate*=100;
        System.out.println("lru, "+phyFrame+" frames Miss rate: "+missRate+"%");
        return missRate;
    }

    public static double fifo(ArrayList<Integer> al,int phyFrame,boolean flag)
    {
        boolean fault =false;
        int miss=0;
        int ref=0;
        int arr[] = new int[phyFrame];
        HashSet<Integer> hs = new HashSet<Integer>();
        Arrays.fill(arr, -1);
        int count=0;
        for(int i=0;i<al.size();i++)
        {
            fault=false;
            if(count<phyFrame)
            {

                if(hs.contains(al.get(i)))
                {
                    //hit
                }
                else
                {
                    //miss
                    arr[count]=al.get(i);
                    hs.add(arr[count]);
                    count++;
                }
                
            }
            else
            {
                ref++;
                if(hs.contains(al.get(i)))
                {
                    //hit
                }
                else
                {
                    fault=true;
                    //miss
                    miss++;
                    hs.remove(arr[0]);
                    for(int j=1;j<phyFrame;j++)
                    {
                        arr[j-1]=arr[j];
                    }
                    arr[phyFrame-1]=al.get(i);
                    hs.add(arr[phyFrame-1]);
                    
                }
                
            }
            if(!flag)
                print(arr,fault);
        }
        double missRate = (double)miss/(double)(ref);
        missRate*=100;
        System.out.println("fifo, "+phyFrame+" frames Miss rate: "+missRate+"%");
        return missRate;
    }

    public static void print(int arr[],boolean fault)
    {
        
        System.out.print("[");
        for(int j=0;j<arr.length;j++)
        {

            if(j==0)
            {
                System.out.print(arr[j]);
                continue;
            }
            if(arr[j]==-1)
            {
                System.out.print("|");
            }
            else
            {
                System.out.print("|"+arr[j]);
            }
        }
        if(fault)
            System.out.println("] F");
        else
        {
            System.out.println("]");
        }
    }

}