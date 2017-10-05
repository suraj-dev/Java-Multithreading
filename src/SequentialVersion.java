import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* This class represents the Sequential version of the program. 
* Class contains methods that create threads, start the threads, parses the input array list passed to the thread,
* accumulates station's max temperatures to a hashmap, calculate average and print results.
*/
public class SequentialVersion {
	/*
	 * list used to contain strings from the weather data to be parsed.
	 */
	private List<String> excelData;
	/*
	 * hashmap to contain key-value pairs of stationId and its station data that contains the sum and count of 
	 * TMAX values.
	*/
	private HashMap<String, StationData> accumulatedData = new HashMap<>();
	private String delimiter = ",";
	
	SequentialVersion(List<String> data) {
		excelData = data;
	}
	
	/*
	 * this method parses the list of strings for TMAX values, extracts the value and stationId and pushes it to the
	 * shared hashmap as a stationId, value pair.
	 */
	public void dataAccumulator() {
		for(String line: excelData) {
			String[] stationValues = line.split(delimiter);
			//System.out.println(stationValues[2]);
			if(stationValues[2].replaceAll("\\s+","").equals("TMAX")) {
				//System.out.println("found TMAX");
				String stationId = stationValues[0].replaceAll("\\s+","");
				float temperature = Float.parseFloat(stationValues[3].replaceAll("\\s+",""));
				//System.out.println(stationId + ": " + temperature);
				if(!accumulatedData.containsKey(stationId)) {
					StationData station = new StationData();
					//Computation for first 17 values
					Fibonacci fb = new Fibonacci(17);
					station.updateSum(temperature);
					accumulatedData.put(stationId, station);
				}
				else {
					StationData station = accumulatedData.get(stationId);
					//Computation for first 17 values
					Fibonacci fb = new Fibonacci(17);
					station.updateSum(temperature);
				}
			}
		}
	}
	
	/*
	 * this method iterates through the shared HashMap and calculates the average for each station
	 */
	public void calculateAverage() {
		Iterator it = accumulatedData.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			StationData station = (StationData) pair.getValue();
			float average = station.sum/station.count;
			station.average = average;
		}
	}
	
	/*
	 * this method iterates through the hashmap and prints the average temperature for each station
	 */
	public void printResults() {
		Iterator it = accumulatedData.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			StationData station = (StationData) pair.getValue();
			System.out.println("StationId: " + pair.getKey() + " average temperature: " + station.average);
		}
	}
	
}
