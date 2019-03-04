import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PowerArrayApp {
	
   public static void main(String[] args) {
	  String CSVName = "/home/marshmewllow/Desktop/Engineering/2019/CSC2001F/MyRepo/Assignment1/cleaned_data.csv";
	  timeStamp[] powerReadings = CSVread(CSVName);
	  
  	if(args.length > 0) {
  		printDateTime(powerReadings, args[0]);
	}else {
		  printAllDateTimes(powerReadings);
	}
	  

	}
    
   
   public static void printDateTime(timeStamp[] powerReadings, String Search) {
	   int i = 0;	
	   int j = 0;
		while(i < powerReadings.length) { 
			if(powerReadings[i] != null) {
				if( powerReadings[i].time.contains(Search)) {
					System.out.println("Search :" + Search);
					System.out.println("Date/Time            " + "Global active power  " + "voltage  ");
					System.out.println(powerReadings[i].time +"  "+ powerReadings[i].global_active_power +"              "+ powerReadings[i].voltage);
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
			}
			i ++; 
		}
   }
	
   public static timeStamp[] CSVread(String FileName){
       String line = "";

   //Declaring array
   timeStamp[] powerValues;
   powerValues = new timeStamp[510];	

   
   try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {

	   int lineNo = 0;
       while ((line = br.readLine()) != null) {
           String[] Element = line.split(",");
       
       int i = 0;
       int size = Element.length;
	       while(i < size) {
	    	   	if (lineNo > 0) {
		   	       powerValues[lineNo] = new timeStamp(Element[3],Element[1],Element[0]);
	    	   	}	   	       
	    	   	i ++;	
	       }               
       lineNo ++;
       }

       } 
       catch (IOException e) {
           e.printStackTrace();
       }
       return powerValues;
   }
}






