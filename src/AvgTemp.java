import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * AvgTemp is the class containing the main method of the program.
 * It contains the code for executing each version of the program.
 * If you want to run a specific version of the program, comment out the other versions below in the class.
 * Each version is identified by a header comment having the name of the version
 */
public class AvgTemp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Starting program");
		//path to CSV file is passed through command line argument
		ExcelReader er = new ExcelReader(args[0]);
		//excel reader object reads data from CSV file
		er.read();
		//ArrayList used to store strings read from CSV file
		List<String> excelData = er.getData();
		int length = excelData.size();
		for(String line: excelData) {
			System.out.println(line);
		}
		
		
		//...................................................................................
		//Sequential version
		long[] sequentialTimes = new long[10];
		for(int i = 0; i<10; i++) {
			SequentialVersion v1 = new SequentialVersion(excelData);
			//start time
			long sequentialExecutionStartTime = System.currentTimeMillis();
			//accumulates data in a data structure like HashMap
			v1.dataAccumulator();
			//computes the average TMAX values for each station
			v1.calculateAverage();
			//end time
			long sequentialExecutionEndTime = System.currentTimeMillis();
			//v1.printResults();
			long timeTaken = sequentialExecutionEndTime - sequentialExecutionStartTime;
			//System.out.println("Time taken for parsing data and average computation using sequential execution: " + timeTaken);
			sequentialTimes[i] = timeTaken;
		}
		TimeCalculator tc = new TimeCalculator(sequentialTimes);
		//computes the minimum, maximum and average time values
		tc.computeTime();
		//................................................................................
		
		
		//NoLock version
		long[] noLockTimes = new long[10];
		for(int i = 0; i<10; i++) {
			//start time
			long noLockExecutionStartTime = System.currentTimeMillis();
			//ArrayList containing first half of excel data
			List<String> firstHalf = excelData.subList(0, length/2);
			//ArrayList containing second half of excel data
			List<String> secondHalf = excelData.subList((length/2) + 1, length);
			NoLock th1 = new NoLock("thread1", firstHalf);
			NoLock th2 = new NoLock("thread2", secondHalf);
			th1.start();
			th2.start();
			try {
			th1.t.join();
			th2.t.join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			//computes the average TMAX values for each station
			NoLock.calculateAverage();
			//end time
			long noLockExecutionEndTime = System.currentTimeMillis();	
			long timeTaken = noLockExecutionEndTime - noLockExecutionStartTime;
			noLockTimes[i] = timeTaken;
		}
		TimeCalculator nltc = new TimeCalculator(noLockTimes);
		//computes the minimum, maximum and average time values
		nltc.computeTime();
		//NoLock.printResults();
		//System.out.println("noLock execution time taken: " + (noLockExecutionEndTime - noLockExecutionStartTime));
		//.............................................................................................
		
		//CoarseLock version
		long[] coarseLockTimes = new long[10];
		for(int i = 0; i<10; i++) {
			//start time
			long coarseLockExecutionStartTime = System.currentTimeMillis();
			//ArrayList containing first half of excel data
			List<String> firstHalfForCoarseLock = excelData.subList(0, length/2);
			//ArrayList containing second half of excel data
			List<String> secondHalfForCoarseLock = excelData.subList((length/2) + 1, length);
			CoarseLock cl1 = new CoarseLock("thread1", firstHalfForCoarseLock);
			CoarseLock cl2 = new CoarseLock("thread2", secondHalfForCoarseLock);
			cl1.start();
			cl2.start();
			try {
			cl1.t.join();
			cl2.t.join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			//computes the average TMAX values for each station
			CoarseLock.calculateAverage();
			//end time
			long coarseLockExecutionEndTime = System.currentTimeMillis();
			long timeTaken = coarseLockExecutionEndTime - coarseLockExecutionStartTime;
			coarseLockTimes[i] = timeTaken;
//			//CoarseLock.printResults();
//	//		System.out.println("coarseLock execution time taken: " + (coarseLockExecutionEndTime - coarseLockExecutionStartTime));
		}
		TimeCalculator cltc = new TimeCalculator(coarseLockTimes);
		//computes the minimum, maximum and average time values
		cltc.computeTime();
		//..............................................................................................
		
		//FineLocking version
		long[] fineLockTimes = new long[10];
		for(int i = 0; i<10; i++) {
			//start time
			long fineLockExecutionStartTime = System.currentTimeMillis();
			//ArrayList containing first half of excel data
			List<String> firstHalfForFineLock = excelData.subList(0, length/2);
			//ArrayList containing second half of excel data
			List<String> secondHalfForFineLock = excelData.subList((length/2) + 1, length);
			FineLocking fl1 = new FineLocking("thread1", firstHalfForFineLock);
			FineLocking fl2 = new FineLocking("thread2", secondHalfForFineLock);
			fl1.start();
			fl2.start();
			try {
			fl1.t.join();
			fl2.t.join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			//computes the average TMAX values for each station
			FineLocking.calculateAverage();
			//end time
			long fineLockExecutionEndTime = System.currentTimeMillis();
			long timeTaken = fineLockExecutionEndTime - fineLockExecutionStartTime;
			fineLockTimes[i] = timeTaken;
			//FineLocking.printResults();
			//System.out.println("fineLock execution time taken: " + (fineLockExecutionEndTime - fineLockExecutionStartTime));
		}
		TimeCalculator fltc = new TimeCalculator(fineLockTimes);
		//computes the minimum, maximum and average time values
		fltc.computeTime();
		//.......................................................................................
		
		//noSharing version
		long[] noSharingTimes = new long[10];
		for(int i = 0; i<10; i++) {
			//start time
			long noSharingExecutionStartTime = System.currentTimeMillis();
			//ArrayList containing first half of excel data
			List<String> firstHalfForNoSharing = excelData.subList(0, length/2);
			//ArrayList containing second half of excel data
			List<String> secondHalfForNoSharing = excelData.subList((length/2) + 1, length);
			NoSharing ns1 = new NoSharing("thread1", firstHalfForNoSharing);
			NoSharing ns2 = new NoSharing("thread2", secondHalfForNoSharing);
			ns1.start();
			ns2.start();
			try {
			ns1.t.join();
			ns2.t.join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			//station mappings from thread 1
			HashMap<String, StationData> firstMapping= ns1.getMappings();
			//station mappings from thread 2
			HashMap<String, StationData> secondMapping= ns2.getMappings();
			//merge results from the 2 mappings into a single mapping and calculate running average for each station
			Iterator it = firstMapping.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				String key = (String) pair.getKey();
				StationData value = (StationData) pair.getValue();
				if(secondMapping.containsKey(key)) {
					StationData finalStationObject = (StationData) secondMapping.get(key);
					finalStationObject.sum += value.sum;
					finalStationObject.count += value.count;
					finalStationObject.average = finalStationObject.sum/finalStationObject.count;
				}
				else {
					value.average = value.sum/value.count;
					secondMapping.put(key, value);
				}
			}
			//end time
			long noSharingExecutionEndTime = System.currentTimeMillis();
			long timeTaken = noSharingExecutionEndTime - noSharingExecutionStartTime;
			noSharingTimes[i] = timeTaken;
//			Iterator res = secondMapping.entrySet().iterator();
//			while(res.hasNext()) {
//				Map.Entry pair = (Map.Entry)res.next();
//				StationData station = (StationData) pair.getValue();
//				System.out.println("StationId: " + pair.getKey() + " average temperature: " + station.average);
//			}
//		System.out.println("noSharing execution time taken: " + (noSharingExecutionEndTime - noSharingExecutionStartTime));
		}
		TimeCalculator nstc = new TimeCalculator(noSharingTimes);
		//computes the minimum, maximum and average time values
		nstc.computeTime();
		//..........................................................................................
	}
	
	
}
