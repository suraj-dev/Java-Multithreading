/*
 * StationData class holds the data for each station in the weather data.
 * It holds the running sum for TMAX values, the count and the final average.
 * It contains a method to update the running sum for TMAX values.
 */
public class StationData {
	int count = 0;
	float sum = 0;
	float average;
	
	/*
	 * this method updates the running sum for TMAX and increments the count for each update.
	 */
	public void updateSum(float value) {
		sum += value;
		count++;
	}
}
