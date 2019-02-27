import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class vmstat {

	public static void main(String[] args) throws Exception {
		try{
		
            Engine e = new Engine();
            
			int minframes = Integer.parseInt(args[0]);
			int maxframes = Integer.parseInt(args[1]);
			int inc = Integer.parseInt(args[2]);
			String input = args[3];
			File file = new File(input);
            ArrayList<Integer> al = new ArrayList<Integer>();
            Scanner sc = new Scanner(file);
            while(sc.hasNextInt())
            {
                al.add(sc.nextInt());
            }
    
    
    
    
        PrintWriter pw = new PrintWriter("vmrates.dat");
        for(int i=minframes;i<=maxframes;i+=inc)
        {
            pw.print(i+"  ");
        }
        pw.println();
        
        for(int i = minframes;i <= maxframes;i += inc) {
            double x=e.lru(al,i,true);
            pw.print(x+"  ");
        }
        pw.println();
        for(int i = minframes;i <= maxframes;i += inc) {
            double x=e.opt(al, i, true);
            pw.print(x+"  ");
            
        }
        pw.println();
        for(int i = minframes;i <= maxframes;i += inc) {
            double x=e.fifo(al,i,true);
            pw.print(x+"  ");
        }
        pw.println();
        pw.close();
    }
    catch(Exception e1)
    {
        System.out.println(e1.toString());
    }
           
		
		
	}

}
