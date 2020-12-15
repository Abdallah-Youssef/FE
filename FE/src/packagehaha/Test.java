package packagehaha;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

class Test {

	class Pair {
		int first, second;
		public Pair(int f, int s) {
			first = f;
			second = s;
		}
	}

	
	public enum Implementation {
	    NAIVE_1, NAIVE_2, FAST_ITERATIVE, FAST_RECURSIVE
	}
	
	@org.junit.jupiter.api.Test
	void test() {

		/*for (int i = 0;i < 32;i++) {
			maxInt(i);
		}*/
		Map<Implementation, ExpoI> map = new EnumMap<Implementation, ExpoI>(Implementation.class);
		
		map.put(Implementation.NAIVE_1, new Naive_1());

		map.put(Implementation.NAIVE_2, new Naive_2());

		map.put(Implementation.FAST_ITERATIVE, new FastIterative());

		map.put(Implementation.FAST_RECURSIVE, new FastRecursive());
		
	
	
		//                   ExpoI          m      n_tests  n_bits_range    name
		//plotAvgTime_NBits(new Naive_1(), 46000, 10000, new Pair(3, 14), "Naive_1");
		plotAvgTime_NBits(new Naive_2(), 46000, 1000, new Pair(3, 25), "Naive_2");
		//plotAvgTime_NBits(new FastIterative(), 46000, 10000, new Pair(3, 31), "Fast Iterative");
		//plotAvgTime_NBits(new FastRecursive(), 46000, 10000, new Pair(3, 31), "Fast Recursive");
		
		
		
		//plotOverflows_M(new Naive_1(), 17, 1000, new int[] {10, 1000000, 10000}, "Naive_1");
		//plotOverflows_M(new Naive_2(), 17, 1000, new int[] {40000, 70000, 1000}, "Naive_2");
		//plotOverflows_M(new FastIterative(), 17, 1000, new int[] {40000, 70000, 1000}, "Fast Iterative");
		//plotOverflows_M(new FastRecursive(), 17, 1000, new int[] {40000, 70000, 1000}, "Fast Recursive");
		
		
		
		
		
		int m = 47000, n_test = 10000;
		for (Implementation imp : Implementation.values()) {
			for (int i = 0;i < 100000;i++) {
				Integer a = randomInt(4);
				Integer b = randomInt(3);
				
				int mine = map.get(imp).pow(a, b, m);
				long built_in = Math.round(Math.pow(a, b)) % m;
				
				/*System.out.println(a + " " + b);
				System.out.println(x + " " + y);*/
				
				if (mine != -1)
					assertEquals(mine, built_in);
			}
		}
		
		
	}
	
	
	void plotOverflows_M(ExpoI method, int n_bits, int n_test, int[] m_range, String name) {
		
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> y = new ArrayList<Double>();
		
		
		for (int m = m_range[0];m <= m_range[1]; m+= m_range[2]) {
			System.out.println("m = " + m);
			
			int[][] testSet = getTestSet(n_test, n_bits);
			
			double[] results = getAvgTime(method, testSet, m);
			System.out.println(name + ": avg = " + results[0] + ", overflows = " + results[1]); 
			
			x.add((double) m);
			y.add(results[1]);
				
		}
		System.out.println("\n");
		
		plot(x, y, name + " overflows", "m", "Number of overflows out of " + n_test + " tests");
		
		
	}
	
	void plotAvgTime_NBits(ExpoI method, int m, int n_test, Pair bits_range, String name) {
		
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> y = new ArrayList<Double>();
		
		
		for (int n_bits = bits_range.first;n_bits <= bits_range.second;n_bits++) {
			System.out.println("N_bits = " + n_bits);
			
			int[][] testSet = getTestSet(n_test, n_bits);
			
				
			double[] results = getAvgTime(method, testSet, m);
			System.out.println(name + ": avg = " + results[0] + ", overflows = " + results[1]); 
			
			x.add((double) n_bits);
			y.add(results[0] / 1000);
			
			
			System.out.println("\n");
		}
		
		x.remove(0);
		y.remove(0);
		plot(x, y, name, "number of bits", "Time in milli seconds" );
	}
	
	void plot (ArrayList<Double> x, ArrayList<Double> y, String name, String xAxis, String yAxis) {
		Plot plot = Plot.plot(Plot.plotOpts().
		        title(name).
		        legend(Plot.LegendFormat.BOTTOM))
				.xAxis(xAxis, null).
			    yAxis(yAxis, null).
		    series("Data", Plot.data().
		        xy(x, y),
		        Plot.seriesOpts().
		            marker(Plot.Marker.DIAMOND).
		            markerColor(Color.GREEN).
		            color(Color.BLACK));
		
		try {
			plot.save(name, "png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	double[] getAvgTime (ExpoI exp, int[][] testSet, int m) {
		long sum = 0, n_test = testSet.length, overflows = 0;
			
		for (int i = 0; i < n_test;i++) {
			int a, b;
			a = testSet[i][0];
			b = testSet[i][1];

			
			long startTime = System.nanoTime();
			int res = exp.pow(a, b, m); 
			long stopTime = System.nanoTime();
				
			sum += stopTime - startTime;
			
			
			if (res == -1)
				overflows++;
		}
		
		return new double[]{(double)sum / n_test, overflows};
		
	}
	
	int[][] getTestSet (int n_test, int n_bits) {
		int[][] set = new int[n_test][2];
		for (int i = 0; i < n_test;i++) {
			int a, b;
			a = randomInt(n_bits);
			b = randomInt(n_bits);
			
			set[i][0] = a;
			set[i][1] = b;
		}
		
		return set;
	}
	
	
	//returns a random integer that can be represented using less than or equal to n_bits
	int randomInt(int n_bits) {
		return (int) ((Math.random() * (Math.pow(2, n_bits-1) - 1)) + Math.pow(2, n_bits));
	}
	
	int maxInt(int n_bits) {
		int x = Integer.MAX_VALUE;
		x >>= 31 - n_bits;
		//System.out.println(x + " " + n_bits);
		return x;
	}
	


}
