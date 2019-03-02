import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class PowerBSTApp {
	
   public static void main(String[] args) {
		String CSVName = "/home/marshmewllow/Desktop/Engineering/2019/CSC2001F/MyRepo/Assignment1/cleaned_data.csv";
		List<timeStamp> powerReadings = CSVread(CSVName);
		Collections.sort(powerReadings);
	}
    
   
   public static void printDateTime(timeStamp[] powerReadings, String Search) {
	   int i = 0;	
	   int j = 0;
		while(i < powerReadings.length) { 
			if(powerReadings[i] != null) {
				if( powerReadings[i].time.contains(Search)) {
					System.out.println("Search :" + Search);
					System.out.println("Date/Time :          " + "Global active power : " + "voltage :");
					System.out.println(powerReadings[i].time +"  "+ powerReadings[i].global_active_power +"               "+ powerReadings[i].voltage);
					i = powerReadings.length;
					j = 1;
				}
			}
			i++;
		}
		if(j ==0 ) {
			System.out.println("Search for " + Search);
			System.out.println("Date/Time not found");
		}
   }
	
   public static void printAllDateTimes(timeStamp[] powerReadings){
		int i = 1;
		while(i < powerReadings.length) { 
			if(powerReadings[i] != null) {
				System.out.println(powerReadings[i].time +"   "+ powerReadings[i].global_active_power +"   "+powerReadings[i].voltage ); 
				i ++; 	
			}
		}
   }
	
   public static List<timeStamp> CSVread(String FileName){
       String line = "";

   //Declaring array
   List<timeStamp> powerValues = new ArrayList<>();

   
   try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {

	   int lineNo = 0;
       while ((line = br.readLine()) != null) {
           String[] Element = line.split(",");
           powerValues.add(new timeStamp(Element[3],Element[1],Element[0]));

       }

       } 
       catch (IOException e) {
           e.printStackTrace();
       }
       return powerValues;
   }
}






