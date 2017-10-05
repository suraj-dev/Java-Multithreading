import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;

/*
 * ExcelReader class contains methods to read data from a CSV file and return the data.
 */
public class ExcelReader {
	//path of the excel data
	private String path;
	
	//a list to store each line parsed from the excel file
	List<String> excelData = new ArrayList<>();
	String line = "";
	
	ExcelReader(String param) {
		path = param;
	}
	
	/*
	 * this method reads data from a CSV file line by line and stores each line as a string in
	 * an ArrayList.
	 */
	public void read() {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            while ((line = br.readLine()) != null) {
            	excelData.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/*
	 * this method returns the ArrayList used to store the strings from the CSV file
	 */
	public List<String> getData() {
		return excelData;
	}
}
