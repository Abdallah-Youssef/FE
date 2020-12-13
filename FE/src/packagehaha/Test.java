package packagehaha;

import java.util.EnumMap;
import java.util.Map;

class Test {

	
	public enum Implementation {
	    NAIVE_1, NAIVE_2, FAST_ITERATIVE, FAST_RECURSIVE
	}
	
	@org.junit.jupiter.api.Test
	void test() {


		FastExpo exp = new FastExpo();
		
		Map<Implementation, ExpI> map = new EnumMap<Implementation, ExpI>(Implementation.class);
		
		map.put(Implementation.NAIVE_1, new ExpI() {
			public int pow(int a, int b, int m) {
				return exp.naive_1(a, b, m);
			}
		});
		
		map.put(Implementation.NAIVE_2, new ExpI() {
			public int pow(int a, int b, int m) {
				return exp.naive_2(a, b, m);
			}
		});
		
		map.put(Implementation.FAST_ITERATIVE, new ExpI() {
			public int pow(int a, int b, int m) {
				return exp.fast_expo_iterative(a, b, m);
			}
		});
		
		map.put(Implementation.FAST_RECURSIVE, new ExpI() {
			public int pow(int a, int b, int m) {
				return exp.fast_expo_recursive(a, b, m);
			}
		});
		
		
		
	
		//play with the range of the n_bits to see how each scales
		for (int n_bits = 10;n_bits <= 18;n_bits++) {
			System.out.println("N_bits = " + n_bits);
			
			for (Implementation imp : Implementation.values()) { 
				double[] results = getAvgTime(map.get(imp), n_bits);
			    System.out.println(imp + ": avg = " + results[0] + ", overflows = " + results[1]); 
			}
			System.out.println("\n");
		}
		
	}
	
	double[] getAvgTime (ExpI exp, int n_bits) {
		long sum = 0, n_test = 10000, overflows = 0;
			
		for (int i = 0; i < n_test;i++) {
			Integer a = randomInt(n_bits);
			Integer b = randomInt(n_bits);
			Integer m = randomInt(n_bits) % 40000 + 1000;
			
			long startTime = System.nanoTime();
			int res = exp.pow(a, b, m); 
			long stopTime = System.nanoTime();
			
			if (res == -1)
				overflows++;
				
			sum += stopTime - startTime;
		}
		
		return new double[]{(double)sum / n_test, overflows};
		
	}
	
	
	//returns a random integer that can be represented using less than or equal to n_bits
	Integer randomInt(int n_bits) {
		return (int) (Math.random() * (Math.pow(2, n_bits) - 1));
	}
	
	
	interface ExpI{
		int pow (int a, int b, int m);
	}
		

}
