import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PowerArrayApp {
	
   public static void main(String[] args) {
	    opCount count = new opCount(0);
	    String CSVName = "/home/marshmewllow/Desktop/Engineering/2019/CSC2001F/MyRepo/Assignment1/cleaned_data.csv";
	    timeStamp[] powerReadings = CSVread(CSVName, count);
	    count.opCount = count.opCount + 1;
	  	if(args.length > 0) {
	  		if("-c" .equals(args[0])) {
	  			printAllDateTimes(powerReadings, count);
	  			System.out.println("Total operation count = " +count.opCount);	
	  		}else {
		  		printDateTime(powerReadings, args[0], count);  		
		  		for(int i = 0; i < (args.length) ; i++) {
		  			if("-c" .equals(args[i])) {
		  				System.out.println("Total operation count = " +count.opCount);	
		  			}
		  		}
	  			
	  		}	
		}else {
			  printAllDateTimes(powerReadings, count);
		  		for(int i = 0; i < (args.length) ; i++) {
		  			if(args[i] == "-c") {
		  				System.out.println("Total operation count = " +count.opCount);	
		  			}
		  		}
		}
   }
    
   
   public static void printDateTime(timeStamp[] powerReadings, String Search, opCount count) {
	   int i = 0;	
	   int j = 0;
	   count.opCount = count.opCount + 1;
		while(i < powerReadings.length) {
		    count.opCount = count.opCount + 1;
		    count.opCount = count.opCount + 1;
			if(powerReadings[i] != null) {
				count.opCount = count.opCount + 1;
				if( powerReadings[i].time.contains(Search)) {
					System.out.println("");
					System.out.println("Search :" + Search);
					System.out.println("Date/Time            " + "Global active power  " + "voltage  ");
					System.out.println(powerReadings[i].time +"  "+ powerReadings[i].global_active_power +"              "+ powerReadings[i].voltage);
					i = powerReadings.length;
					j = 1;
				}
			}
			i++;
		}
		count.opCount = count.opCount + 1;
		if(j ==0 ) {
			System.out.println("Search for " + Search);
			System.out.println("Date/Time not found");
		}
   }
	
   public static void printAllDateTimes(timeStamp[] powerReadings, opCount count){
		int i = 1;
		count.opCount = count.opCount + 1;
		while(i < powerReadings.length) {
		    count.opCount = count.opCount + 1;
		    count.opCount = count.opCount + 1;
			if(powerReadings[i] != null) {
				System.out.println(powerReadings[i].time +"   "+ powerReadings[i].global_active_power +"   "+powerReadings[i].voltage ); 	
			}
			i ++; 
		}
   }
	
   public static timeStamp[] CSVread(String FileName, opCount count){
       String line = "";
	   //Declaring array
	   timeStamp[] powerValues;
	   powerValues = new timeStamp[510];	
	
	   
	   try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {
	
		   int lineNo = 0;
		   count.opCount = count.opCount + 1;
	       while ((line = br.readLine()) != null) {
	   	    count.opCount = count.opCount + 1;
	           String[] Element = line.split(",");
	       
	       int i = 0;
	       int size = Element.length;
	       count.opCount = count.opCount + 1;
	       while(i < size) {
	   	    	count.opCount = count.opCount + 1;
	   	    	count.opCount = count.opCount + 1;
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






