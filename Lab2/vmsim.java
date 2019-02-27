import java.util.*;
import java.io.*;



public class vmsim
{


   
    public static void main(String[] args) throws Exception {
        Engine e = new Engine();
        
        int phyFrame= Integer.parseInt(args[0]);

        //reading inputs from a file
        File file = new File(args[1]);
        Scanner sc = new Scanner(file);
        ArrayList<Integer> al = new ArrayList<>();
        
        while(sc.hasNextInt())
        {
            al.add(sc.nextInt());
        }
        String s = args[2];

        if(s.equals("opt"))
        {
            e.opt(al, phyFrame,false);
        }

        else if(s.equals("lru"))
        {
            e.lru(al, phyFrame,false);
        }
        else
        {
            e.fifo(al, phyFrame,false);
        }


    }
}