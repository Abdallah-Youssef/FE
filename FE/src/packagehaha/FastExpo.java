package packagehaha;

import java.util.Scanner;

public class FastExpo {
	static Scanner in = new Scanner(System.in);

	
	public static void main (String[] args) {
		int a, b, m;
		FastExpo expo = new FastExpo();
		a = inputInt("Enter a: ");
		b = inputInt("Enter b: ");
		m = inputInt("Enter m: ");
		
		System.out.println(expo.naive_1(a, b, m));
		System.out.println(expo.naive_2(a, b, m));
		System.out.println(expo.fast_expo_recursive(a, b, m));
		System.out.println(expo.fast_expo_iterative(a, b, m));
		
		
		
		in.close();
		return;
	}
	
	Integer naive_1(int a, int b, int m) {
		int c = 1;
		for (int i = 0;i < b;i++) {
			if (mul_overflows(c, a)) {
				System.out.println("C overflowed at b = " + i);
				break;
			}
			
			c = Math.multiplyExact(c, a);
		}
		
		c %= m;
		return c;
	}
	
	Integer naive_2(int a, int b, int m) {
		int c = 1;
		for (int i = 0;i < b;i++) {
			if (mul_overflows(c, a)) {
				System.out.println("C overflowed at b = " + i);
				break;
			}
			
			
			c = Math.multiplyExact(c, a);
			c %= m;
		}
		
		return c;
	}

	Integer fast_expo_recursive(int a, int b, int m) {
		if (b == 0)
			return 1;
		
		int ret = fast_expo_recursive (a, b / 2, m);
		ret %= m;
		
		if (mul_overflows(ret, ret)) {
			System.out.println("ret * ret = " + ret + "*" + ret + "\nOverflows at b = " + b);
			return 0;
		}
	
		if (b % 2 == 0) {
			return (ret * ret) % m;
		}
		
		else {
			ret *= ret;
			ret %= m;
			
			if (mul_overflows(ret, a)) {
				System.out.println("ret * a = " + ret + "*" + a + "\nOverflows at b = " + b);
				return 0;
			}

			return (ret * a) % m;
			
		}
	}
	
	Integer fast_expo_iterative(int a, int b, int m) {
		if (b == 0)
			return 1;
		
		int c = 1;
		while (b > 0) {
			if ((b & 1) == 1) {
				if (mul_overflows(c, a)) {
					System.out.println("(c * a) = " + c + "*" + a + "\nOverflows at b = " + b);
					return 0;
				}
				
				c *= a;
				c %= m;
			}
				
				
			if (mul_overflows(a, a)) {
				System.out.println("(a * a)= " + a + "*" + a + "\nOverflows at b = " + b);
				return 0;
			}
			a *= a;
			a %= m;
			
			b >>= 1;
				
			
		}
		
		return c;
	}
	
	
	boolean mul_overflows(int x, int y) {
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
