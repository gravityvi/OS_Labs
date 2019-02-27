import java.io.PrintWriter;
import java.util.Random;

public class vmgen {

	public static void main(String[] args) {
		
		try {
			int pageRef = Integer.parseInt(args[0]);
			int length = Integer.parseInt(args[1]);
			String name = args[2];
			PrintWriter w = new PrintWriter(name);
			Random r = new Random();
			int[] arr = new int[length];
			for(int i = 0;i < length;i++) {
				int num = r.nextInt(pageRef);
				if(i == 0) {
					arr[i] = num;
				}else {
					if(num == arr[i-1]) {
						if(num == pageRef-1 && num == 0) {
							System.out.println("Data cannot be generated");
							System.exit(0);
						}else if(num == 0) {
							num++;
						}else if(num == pageRef-1) {
							num--;
						}else {
							num++;
						}
					}
					arr[i] = num;
				}
			}
			for(int i = 0;i < length;i++) {
				w.print(arr[i] + " ");
			} 
			w.close();
		}
		catch(Exception E) {
			System.out.println("Invalid Arguments");
			System.exit(0);
		}
		
	}

}
