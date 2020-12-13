package packagehaha;


class Test {

	@org.junit.jupiter.api.Test
	void test() {
		for (int i = 0;i < 10000;i++) {
			FastExpo exp = new FastExpo();
		
			int a = (int) (Math.random() * 10000);
			int b = (int) (Math.random() * 5);
			int m = (int) (Math.random() * 10000 + 70000);
			
			System.out.println(a + " " + b + " " + m);
			System.out.println((Math.pow(a, b) % m));
			System.out.println((int)exp.fast_expo_iterative(a, b, m));
			System.out.println("===========================================");
			
		}
	}
	
	
	//returns a random integer that can be represented using less than or equal to n_bits
	Integer randomInt(int n_bits) {
		return (int) (Math.random() * (Math.pow(2, n_bits) - 1));
	}
	
	
		

}
