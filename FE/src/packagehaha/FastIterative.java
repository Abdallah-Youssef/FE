package packagehaha;

public class FastIterative implements ExpoI {

	public int pow(int a, int b, int m) {
		if (b == 0)
			return 1;
		
		a %= m;
		int c = 1;
		while (b > 0) {
			if ((b & 1) == 1) {
				if (Utils.mul_overflows(c, a)) 
					//System.out.println("(c * a) = " + c + "*" + a + "\nOverflows at b = " + b);
					return -1;
				
				
				c *= a;
				c %= m;
			}
				
				
			if (Utils.mul_overflows(a, a)) 
				//System.out.println("(a * a)= " + a + "*" + a + "\nOverflows at b = " + b);
				return -1;
			
			a *= a;
			a %= m;
			
			b >>= 1;
		}
		
		return c;
	}

}
