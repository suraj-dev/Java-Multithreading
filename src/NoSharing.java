import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* This class represents the NoSharing version of the program. 
* Class contains methods that create threads, start the threads, parses the input array list passed to the thread,
* accumulates station's max temperatures to a hashmap, calculate average and print results.
*/
public class NoSharing extends Thread{
	/*
	 * hashmap to contain key-value pairs of stationId and its station data that contains the sum and count of 
	 * TMAX values. Each thread will have its own copy of a HashMap
	*/
	HashMap<String, StationData> accumulator = new HashMap<>();
	private String threadName;
	public Thread t;
	/*
	 * list used to contain strings from the weather data to be parsed by the thread
	 */
	private List<String> data= new ArrayList<>();
	private String delimiter = ",";
	
	NoSharing(String name, List<String> inputData) {
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
	 * returns the HashMap associated to the current thread
	 */
	public HashMap<String, StationData> getMappings() {
		return accumulator;
	}
}
