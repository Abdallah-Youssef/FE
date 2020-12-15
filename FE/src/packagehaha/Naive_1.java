package packagehaha;

public class Naive_1 implements ExpoI {

	public int pow(int a, int b, int m) {
		int c = 1;
		for (int i = 0;i < b;i++) {
			if (Utils.mul_overflows(c, a)) {
				//System.out.println("C overflowed at b = " + i);
				return -1;
			}
			
			c *= a;
		}
		
		c %= m;
		return c;
	}

}
