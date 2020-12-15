package packagehaha;

import java.util.Scanner;

public class Utils {
	static Scanner in = new Scanner(System.in);

	static boolean mul_overflows(int x, int y) {
		try {
			Math.multiplyExact(x, y);
			return false;
		}
		catch(ArithmeticException e) {
			return true;
		}
	}
	
	static int inputInt(String prompt) {
		System.out.println(prompt);
		if (in.hasNextInt()) 
			return in.nextInt();
		
		
		throw new RuntimeException();
	}
}
