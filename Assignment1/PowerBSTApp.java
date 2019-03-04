import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class PowerBSTApp {
	
   public static void main(String[] args) {
		//Reading in the CSV file, creating a list of objects and sorting the list.
	    String CSVName = "/home/marshmewllow/Desktop/Engineering/2019/CSC2001F/MyRepo/Assignment1/cleaned_data.csv";
		List<timeStamp> powerReadings = CSVread(CSVName);
		Collections.sort(powerReadings);
		
		BinaryTree theTree = new BinaryTree();
		int listLength = (powerReadings.size());
		
		
//		int i = 0;
//		while(i < listLength) {
//			String time = ((powerReadings.get(i)).getTime()).replaceAll("[/:.,]|12/2006/", "");
//			int key = Integer.valueOf(time);
////			System.out.print(key);
////			System.out.println(powerReadings.get(i));
////			System.out.println(key);
//			theTree.addNode(key, powerReadings.get(i));
//			i ++;
//		}

		
		
    	int start = 0;
    	int end = powerReadings.size();
    	theTree.addNode(theTree.sortedArrayToBST(powerReadings, start, end -1));
//    	System.out.println(((theTree.root).leftChild));
//    	System.out.println((theTree.root).rightChild);
    	theTree.inOrderTraverseTree(theTree.root);	
    	System.out.println(end);
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
	   List<timeStamp> powerValues = new ArrayList<>();
	   int lineNo = 0;
	   
	   try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {
	
	       while ((line = br.readLine()) != null) {
	    	   	if(lineNo > 0) {
		           String[] Element = line.split(",");
		           powerValues.add(new timeStamp(Element[3],Element[1],Element[0]));
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






