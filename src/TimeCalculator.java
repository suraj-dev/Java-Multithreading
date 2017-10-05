import java.util.Arrays;

/*
 * TimeCalculator class contains methods used to compute the average time given an array of time values
 */
public class TimeCalculator {
	//array containing time values
	private long[] times = new long[10];
	
	TimeCalculator(long[] data) {
		times = data;
	}
	
	/*
	 * this method computes the minimum, maximum and average time values from an array of time values.
	 */
	public void computeTime() {
		Arrays.sort(times);
		int len = times.length;
		System.out.println("Minimum time taken: " + times[0] + "ms");
		System.out.println("Maximum time taken: " + times[len - 1] + "ms");
		long sum = 0;
		for(long time: times) {
			 sum += time;
		}
		long average = sum/len;
		System.out.println("Average time taken: " + average + "ms");
	}
}
