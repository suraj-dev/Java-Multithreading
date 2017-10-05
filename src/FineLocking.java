import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* This class represents the FineLock version of the program. 
 * Class contains methods that create threads, start the threads, parses the input array list passed to the thread,
 * accumulates station's max temperatures to a shared hashmap, calculate average and print results 
 * Here, a thread gets a lock on only the accumulation value in the data structure.
 */
public class FineLocking extends Thread{
	/*
	 * Concurrent HashMap to contain key-value pairs of stationId and its station data that contains the sum and count of 
	 * TMAX values. This will be shared by all the threads.
	 * Concurrent HashMap ensures concurrent access 
	*/
	static ConcurrentHashMap<String, StationData> accumulator = new ConcurrentHashMap<>();
	private String threadName;
	public Thread t;
	/*
	 * list used to contain strings from the weather data to be parsed by the thread
	 */
	private List<String> data= new ArrayList<>();
	private String delimiter = ",";
	
	FineLocking(String name, List<String> inputData) {
		threadName = name;
		data = inputData;
		//System.out.println("Creating thread " + threadName);
	}
	
	/*
	 * contains logic for each thread
	 * parses the list of strings for TMAX values, extracts the value and stationId and pushes it to the
	 * shared hashmap as a stationId, value pair.
	 */
	public void run() {
	      //System.out.println("Running " +  threadName );
	      try {
	    	  for(String line: data) {
	  			String[] stationValues = line.split(delimiter);
	  			//System.out.println(stationValues[2]);
	  			if(stationValues[2].replaceAll("\\s+","").equals("TMAX")) {
	  				//System.out.println("found TMAX");
	  				String stationId = stationValues[0].replaceAll("\\s+","");
	  				float temperature = Float.parseFloat(stationValues[3].replaceAll("\\s+",""));
	  				//System.out.println(stationId + ": " + temperature);
	  				if(!accumulator.containsKey(stationId)) {
	  					StationData station = new StationData();
	  				//Computation for first 17 values
	  					Fibonacci fb = new Fibonacci(17);
	  					station.updateSum(temperature);
	  					accumulator.put(stationId, station);
	  				}
	  				else {
	  					StationData station = accumulator.get(stationId);
	  				//Computation for first 17 values
	  					Fibonacci fb = new Fibonacci(17);
	  					station.updateSum(temperature);
	  				}
	  			}
	  		}
	      }catch (Exception e) {
	         e.printStackTrace();
	      }
	      //System.out.println("Thread " +  threadName + " exiting.");
	}
	
	/*
	 * method to create a new thread and start its execution.
	 */
	public void start () {
	      //System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start();
	      }
	}
	
	/*
	 * this method iterates through the shared HashMap and calculates the average for each station
	 */
	public static void calculateAverage() {
		Iterator it = accumulator.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			StationData station = (StationData) pair.getValue();
			float average = station.sum/station.count;
			station.average = average;
			//System.out.println("StationId: " + pair.getKey() + " average temperature: " + average);
		}
	}
	
	/*
	 * this method iterates through the hashmap and prints the average temperature for each station
	 */
	public static void printResults() {
		Iterator it = accumulator.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			StationData station = (StationData) pair.getValue();
			System.out.println("StationId: " + pair.getKey() + " average temperature: " + station.average);
		}
	}
}
