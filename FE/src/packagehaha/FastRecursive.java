package packagehaha;

public class FastRecursive implements ExpoI {

	@Override
	public int pow(int a, int b, int m) {
		
		if (b == 0)
			return 1;
		
		a %= m;
		
		int ret = pow (a, b / 2, m);
		if (ret == -1)
			return -1;
		
		if (Utils.mul_overflows(ret, ret)) {
			//System.out.println("ret * ret = " + ret + "*" + ret + "\nOverflows at b = " + b);
			return -1;
		}
		ret *= ret;
		ret %= m;
		
	
		if (b % 2 == 0) {
			return ret;
		}
		
		else {
			if (Utils.mul_overflows(ret, a)) {
				//System.out.println("ret * a = " + ret + "*" + a + "\nOverflows at b = " + b);
				return -1;
			}

			return (ret * a) % m;
			
		}
		
	}

}
