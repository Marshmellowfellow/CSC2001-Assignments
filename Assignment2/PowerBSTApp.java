import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class PowerBSTApp {
	
   public static void main(String[] args) {
		opCount count = new opCount(0);
		opCount addCount = new opCount(0);
		//Reading in the CSV file, creating a list of objects and sorting the list.
	    String CSVName = "/home/marshmewllow/Desktop/Engineering/2019/CSC2001F/MyRepo/Assignment1/cleaned_data.csv";
		List<timeStamp> powerReadings = CSVread(CSVName, count);
		Collections.sort(powerReadings);
		
		//Creating the BST
		BinaryTree theTree = new BinaryTree();
    	int start = 0;
    	
    	//Control this value to adjust dataSet size using -l.
    	int end = powerReadings.size();
    	if(args.length > 0) {
	  		for(int i = 0; i < (args.length) ; i++) {
	  			if("-l" .contains(args[i])) {
	  				int j = args.length;
	  				if(args.length > i) {
		  					end = Integer.valueOf(args[i + 1]);
	  				}
	  			}else if("-c" .contains(args[i])) {
	  				if(args.length > i) {
  					String fileName = args[i + 1];
  					FileWriter fileWriter;
  						System.out.println("Set size = " + end);
  						System.out.println("Date/Time            Global Avtive Power  Voltage");
  						try {
  							fileWriter = new FileWriter(fileName, true);
  							String text = (String.valueOf(end));
  			  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
  			  				textWriter.write(fileWriter, fileName, text);
  						} catch (IOException e) {
  							e.printStackTrace();
	  					}
	  				}
	  			}
	  		}
    	}
    	
    	//Set max end value to the total number of elements contained to avoid error.
    	if(end > powerReadings.size()) {
    		end = powerReadings.size();
    	}
    	//Sorted Array
    	addCount.opCount = addCount.opCount + theTree.addNode(theTree.sortedArrayToBST(powerReadings, start, end -1, addCount), addCount);
    	
    	if(args.length > 0) {
    		//checking for the -c paramater to print the number total number of comparisons.,
    		if("-c" .contains(args[0])) {
    			theTree.inOrderTraverseTree(theTree.root);
	  			if(args.length > 1) {
	  				String fileName = args[1];
	  				FileWriter fileWriter;
					try {
						fileWriter = new FileWriter(fileName, true);
						String text = (String.valueOf(count.opCount + ", " + addCount.opCount));
		  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
		  				textWriter.write(fileWriter, fileName, text);
					} catch (IOException e) {
						e.printStackTrace();
					}
	  				
	  			}
    			
    		}else if("-l" .contains(args[0])) {
    			
    			theTree.inOrderTraverseTree(theTree.root);
	  			if(args.length > 2) {
		  			if("-c" .equals(args[2])) {
			  			if(args.length > 3) {
			  				String fileName = args[3];
			  				FileWriter fileWriter;
							try {
								fileWriter = new FileWriter(fileName, true);
								String text = (String.valueOf(count.opCount + ", " + addCount.opCount));
				  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
				  				textWriter.write(fileWriter, fileName, text);
							} catch (IOException e) {
								e.printStackTrace();
							}
			  				
			  			}
			  		}
	  			}
    		}else if("-k" .contains(args[0])) {
    			List<String> keys = KEYread(args[1]);
    			if(keys !=null) {
	    			for(int j = 0; j< keys.size();j++) {
	    	    		String time = (keys.get(j)).replaceAll("[/:.,]|12/2006/", "");
	    	    		int key;
	    	    		if(time.length() > 8 ) {
	    	    			key = 0;
	    	    		}else {
	    	    			key = Integer.valueOf(time);
	    	    		}
	    	    		Node search = theTree.findNode(key, count);
	    	    		if(search !=null) {
	    	    			System.out.println((search.name).getTime() + "  " + (search.name).getGlobal_active_power() + "              " + (search.name).getVoltage());
	    		    		for(int i = 0; i < (args.length) ; i++) {
	    		    			if("-c" .contains(args[i])) { 
	    			  				if(args.length > (i+1)) { 
	    				  				String fileName = args[i + 1];
	    				  				FileWriter fileWriter;
	    								try {
	    									fileWriter = new FileWriter(fileName, true);
	    									String text = (String.valueOf(count.opCount + ", " + addCount.opCount));
	    					  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
	    					  				
	    					  				textWriter.write(fileWriter, fileName, text);
	    								} catch (IOException e) {
	    									e.printStackTrace();
	    								}
	    				  			}else {
	    				  			}
	    			  			}
	    			  		}
	    	    		}else {
	    	    			System.out.println("Search for " + keys.get(j));
	    	    			System.out.println("Date/Time not found");
	    		    		for(int i = 0; i < (args.length) ; i++) {
	    		    			if("-c" .contains(args[i])) { 
	    			  				if(args.length > (i+1)) { 
	    				  				String fileName = args[i + 1];
	    				  				FileWriter fileWriter;
	    								try {
	    									fileWriter = new FileWriter(fileName, true);
	    									String text = (String.valueOf(count.opCount + ", " + addCount.opCount));
	    					  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
	    					  				textWriter.write(fileWriter, fileName, text);
	    					  				
	    								} catch (IOException e) {
	    									e.printStackTrace();
	    								}
	    				  			}
	    			  				
	    			  			}else {
	    			  			}
	    			  		}
	    	    		}
	    			}
    			}	
    		}else if("-s" .contains(args[0])){
    			if(args.length > 1) {
		    		String time = args[1].replaceAll("[/:.,]|12/2006/", "");
		    		int key;
		    		if(time.length() > 8 ) {
		    			key = 0;
		    		}else {
		    			key = Integer.valueOf(time);
		    		}
		    		Node search = theTree.findNode(key, count);
		    		if(search !=null) {
			    		System.out.println((search.name).getTime() + "  " + (search.name).getGlobal_active_power() + "              " + (search.name).getVoltage());
			    		for(int i = 0; i < (args.length) ; i++) {
			    			if("-c" .contains(args[i])) { 
			    				System.out.println("Search count = " + count.opCount + " Add count = " + addCount.opCount);
				  				if(args.length > (i+1)) { 
					  				String fileName = args[i + 1];
					  				FileWriter fileWriter;
									try {
										fileWriter = new FileWriter(fileName, true);
										String text = (String.valueOf(count.opCount + ", " + addCount.opCount));
						  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
						  				
						  				textWriter.write(fileWriter, fileName, text);
									} catch (IOException e) {
										e.printStackTrace();
									}
					  			}else {
					  			}
				  			}
				  		}
		    		}else {
		    			System.out.println("");
		    			System.out.println("Search for " + args[0]);
		    			System.out.println("Date/Time not found");
			    		for(int i = 0; i < (args.length) ; i++) {
			    			if("-c" .contains(args[i])) { 
			    				System.out.println("Search count = " + count.opCount + " Add count = " + addCount.opCount);
				  				if(args.length > (i+1)) { 
					  				String fileName = args[i + 1];
					  				FileWriter fileWriter;
									try {
										fileWriter = new FileWriter(fileName, true);
										String text = (String.valueOf(count.opCount + ", " + addCount.opCount));
						  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
						  				textWriter.write(fileWriter, fileName, text);
						  				
									} catch (IOException e) {
										e.printStackTrace();
									}
					  			}
				  				
				  			}else {
				  			}
				  		}
		    		}
    		}
    		}
    	}
    	else {
    		theTree.inOrderTraverseTree(theTree.root);
    	}
    	
    	
    	
    	
	}

   public static void printDateTime(timeStamp[] powerReadings, String Search, opCount count) {
	   int i = 0;	
	   int j = 0;
		while(i < powerReadings.length) { 
			count.opCount = count.opCount + 1;
			if(powerReadings[i] != null) {
				count.opCount = count.opCount + 1;
				if( powerReadings[i].time.contains(Search)) {
					System.out.println("");
					System.out.println("Search :" + Search);
					System.out.println("Date/Time :          " + "Global active power : " + "voltage :");
					System.out.println(powerReadings[i].time +"  "+ powerReadings[i].global_active_power +"               "+ powerReadings[i].voltage);
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
			if(powerReadings[i] != null) {
				System.out.println(powerReadings[i].time +"   "+ powerReadings[i].global_active_power +"   "+powerReadings[i].voltage ); 
				i ++; 	
			}
		}
   }
	
   public static List<timeStamp> CSVread(String FileName, opCount count){
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
   public static List<String> KEYread(String FileName){
	      String line = "";
		   List<String> keys = new ArrayList<>();
		   int lineNo = 0;
		   
		   try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {
		       while ((line = br.readLine()) != null) {
		    	   	if(lineNo > 0) {
			           String[] Element = line.split(",");
			           keys.add(Element[0]);
		       		}
		    	   	lineNo ++;
		       }
		       } 
		       catch (IOException e) {
		           System.out.println("No such file or directory");	
		           return null;
		       }
		       return keys;
	  }
}






