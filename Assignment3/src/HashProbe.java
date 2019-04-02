import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class HashProbe {
	
	static timeStamp[] theArray;
	static int arraySize;
	int itemsInArray = 0;
	static int set = 0;

	
	public static void main(String[] args) {
		opCount insertQ = new opCount(0);
		opCount insertL = new opCount(0);
		opCount searchQ = new opCount(0);
		opCount searchL = new opCount(0);
		opCount maxQ = new opCount(0);
		opCount maxL = new opCount(0);
		opCount tempMaxQ = new opCount(0);
		opCount tempMaxL = new opCount(0);

		
		String CSVName = "cleaned_data.csv";
  		for(int i = 0; i < (args.length) ; i++) {
  			if(("-data" .contains(args[i])) ) {
  				 CSVName = args[i+1];
			}
  		}
		//Setting the size of the table based on closest prime number
		int setSize = 500;
    	if(args.length > 1) {
	  		for(int i = 0; i < (args.length) ; i++) {
	  			if(("-l" .contains(args[i])) && (args[i+1]) != null ) {
						setSize = Integer.parseInt(args[i+1]);
				}
	  		}
    	}
		setSize = isPrime(setSize);
        System.out.println("Set size = " + setSize);
        
        //Reading the CSV
		CSVread dataArray = new CSVread(CSVName, setSize);
		List<timeStamp> dataSet = dataArray.read();
		HashProbe hashImplementation = new HashProbe(setSize);
		
		//Input command determining type of probing to use to build the table.
		
    	if(args.length > 0) {
	  		for(int i = 0; i < (args.length) -1 ; i++) {
	  			if(("-p" .contains(args[i]))) {
	  				if(i < args.length){
		  				if (args[i+1].contains("Q")) {
		  					set = 1;
		  					hashImplementation.quadraticProbe(dataSet, HashProbe.theArray, setSize, insertQ);
		  					//hashImplementation.displayTheStack(setSize);
		  					System.out.println("Quadratic Probe");
		  					System.out.println("");
		  				}
	  				}
				} else if(("-keys" .contains(args[i])) && (args[i+1]) != null ) {
	  				int size = Integer.parseInt(args[i+1]);
	  				hashImplementation.randomKeySet(size);	
				}
	  		}
	  		
    	}
    	if(set ==0) {
			hashImplementation.linearProbe(dataSet, HashProbe.theArray, setSize, insertL);
			//hashImplementation.displayTheStack(setSize);
			System.out.println("Linear Probe");
			System.out.println("");
    	}

		//Searching through the input arguments and running necessary functionality

		List<String> keys = KEYread("keys.txt");
    	if(args.length > 0) {
	  		for(int i = 0; i < (args.length) ; i++) {
	  			if(("-s" .contains(args[i]))) {
	  				System.out.println("------Search Start------");
	  				if(args.length >= i+1) {
		  				if(set == 0) {
			  					searchLinear(args[i+1], setSize, searchL, tempMaxL);
			  					if(tempMaxL.opCount > maxL.opCount) {
			  						maxL.opCount = tempMaxL.opCount;
			  					}
			  					tempMaxL.opCount = 0;
		  				}else {
			  					searchQuadratic(args[i+1], setSize, searchQ, tempMaxQ);
			  					if(tempMaxQ.opCount > maxQ.opCount) {
			  						maxQ.opCount = tempMaxQ.opCount;
			  					}
			  					tempMaxQ.opCount = 0;
		  				}
	  				}
	  			}
	  		}
    	}else {
			if(set == 0) {
				for(int j = 0; j<keys.size();j++) {
					searchLinear(keys.get(j), setSize, searchL, tempMaxL);
					if(tempMaxL.opCount > maxL.opCount) {
						maxL.opCount = tempMaxL.opCount;
					}
					tempMaxL.opCount = 0;
				}
			}else {
				for(int j = 0; j<keys.size();j++) {
					searchQuadratic(keys.get(j), setSize, searchQ, tempMaxQ);
					if(tempMaxQ.opCount > maxQ.opCount) {
						maxQ.opCount = tempMaxQ.opCount;
					}
					tempMaxQ.opCount = 0;
				}
			}
    	}
    	

    	
    
    	for(int i = 0; i < (args.length) ; i++) {	
	  		if("-test" .equals(args[i])) {
	    		if(set == 1) {
    				for(int j = 0; j<keys.size();j++) {
    					searchQuadratic(keys.get(j), setSize, searchQ, tempMaxQ);
    					if(tempMaxQ.opCount > maxQ.opCount) {
    						maxQ.opCount = tempMaxQ.opCount;
    					}
    					tempMaxQ.opCount = 0;
    				}
	    			String fileName = ("test/qudratic.csv");
	  				FileWriter fileWriter;
					try {
						fileWriter = new FileWriter(fileName, true);
                        double loadFactor = 500/((double)setSize);
                        double averageProbeSearch = (searchQ.opCount)/((double)400);
		  				String text = (String.valueOf(insertQ.opCount) + "," + String.valueOf(searchQ.opCount)  + "," + String.valueOf(averageProbeSearch) + "," + String.valueOf(maxQ.opCount) + "," + loadFactor);
		  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
		  				textWriter.write(fileWriter, fileName, text);
					} catch (IOException e) {
						e.printStackTrace();
					}
	    		}else {
    				for(int j = 0; j<keys.size();j++) {
    					searchLinear(keys.get(j), setSize, searchL, tempMaxL);
    					if(tempMaxL.opCount > maxL.opCount) {
    						maxL.opCount = tempMaxL.opCount;
    					}
    					tempMaxL.opCount = 0;
    				}
	  				String fileName = ("test/linear.csv");
	  				FileWriter fileWriter;
					try {
						fileWriter = new FileWriter(fileName, true);
                        double loadFactor = 500/((double)setSize);
                        double averageProbeSearch = (searchL.opCount)/((double)400);
		  				String text = (String.valueOf(insertL.opCount)  + "," + String.valueOf(searchL.opCount)  + "," + String.valueOf(averageProbeSearch) + "," + String.valueOf(maxL.opCount) + "," +  loadFactor);
		  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
		  				textWriter.write(fileWriter, fileName, text);
					} catch (IOException e) {
						e.printStackTrace();
					}
	    		}	
	  		}
    	}
    	System.out.println("");
    	System.out.println("-----Probe counts-----");
    	System.out.println("1) Quadratic Probe Insertion : " + insertQ.opCount);
    	System.out.println("2) Quadratic Probe Search : " + searchQ.opCount);
    	System.out.println("3) Linear Insertion : " + insertL.opCount);
    	System.out.println("4) Linear Search : " + searchL.opCount);
    	
	}
	
	
	
	public ArrayList<String> randomKeySet(int size){
		String CSVName = "cleaned_data.csv";
		CSVread dataArray = new CSVread(CSVName, 499);
		List<timeStamp> dataSet = dataArray.read();
		Collections.shuffle(dataSet);
		ArrayList<String> keyList = new ArrayList<String>();
		for(int i = 0;i< size;i++) {
			keyList.add((dataSet.get(i)).getTime());
			String fileName = "keys.txt";
			FileWriter fileWriter;
			if(i == 0) {
				try {
					fileWriter = new FileWriter(fileName);
	  				String text = ((dataSet.get(i)).getTime());
	  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
	  				textWriter.write(fileWriter, fileName, text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
			try {
				fileWriter = new FileWriter(fileName, true);
  				String text = ((dataSet.get(i)).getTime());
  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
  				textWriter.write(fileWriter, fileName, text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			//searching for each key in the keyset
		}
		return keyList;
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
	
	public static int isPrime(int setSize) {
        int prime = 0;  //next prime will be assigned to this var
        for(int j = setSize; j<10000; j++){  //outer loop

              int count = 0;
              for(int i=2; i<=j/2; i++){  //inner loop

                    if(j%i==0){
                       count++;
                    }                      
              }
              if(count==0){

                    prime = j;   //assign next prime
                    return prime;
              }
        }
        return setSize;
	}

	// The goal is to make the array big enough to avoid
	// collisions, but not so big that we waste memory
	public void linearProbe(List<timeStamp> dataSet, timeStamp[] theArray, int setSize, opCount insertL) {
		for (int n = 1; n < dataSet.size(); n++) {
			insertL.opCount = insertL.opCount + 1;
			timeStamp newElementVal = ((dataSet.get(n)));
			
			// Create an index to store the value in by taking the modulus
			int arrayIndex = Integer.parseInt((newElementVal.getTime()).replaceAll("[/:.,]|12/2006/", "")) % setSize;

			// This is where linear/quadratic and chaining  probing makes a difference
			// Cycle through the array until we find an empty space
			while (theArray[arrayIndex].getTime() != "-1") {
				insertL.opCount = insertL.opCount + 1;
				++arrayIndex;
				//System.out.println("Collision Try " + arrayIndex + " Instead");
				// If we get to the end of the array go back to index 0
				arrayIndex %= arraySize;
			}
			theArray[arrayIndex] = newElementVal;
		}
	}
	
	public void quadraticProbe(List<timeStamp> dataSet, timeStamp[] theArray, int setSize, opCount insertQ) {
		for (int n = 1; n < dataSet.size(); n++) {
			timeStamp newElementVal = ((dataSet.get(n)));
			insertQ.opCount = insertQ.opCount + 1;
			
			// Create an index to store the value in by taking the modulus
			int arrayIndex = Integer.parseInt((newElementVal.getTime()).replaceAll("[/:.,]|12/2006/", "")) % setSize;

			// This is where linear/quadratic and chaining  probing makes a difference
			// Cycle through the array until we find an empty space
			int factor = 0;
			int tempIndex =arrayIndex;
			while (theArray[arrayIndex].getTime() != "-1") {
				insertQ.opCount = insertQ.opCount + 1;
				arrayIndex = tempIndex + factor^2;
				factor++;
				//System.out.println("Collision Try " + arrayIndex + " Instead");
				// If we get to the end of the array go back to index 0
				arrayIndex %= arraySize;
			}
			theArray[arrayIndex] = newElementVal;
		}
	}

	public static timeStamp searchLinear(String key, int setSize, opCount searchL, opCount tempMaxL) {
		// Find the keys original HashProbe key
		String stringKey = key.replaceAll("[/:.,]|12/2006/", "");
		int intKey = Integer.parseInt(stringKey);
		int arrayIndexHash = intKey % setSize;
		while (theArray[arrayIndexHash].getTime() != "-1") {
			searchL.opCount = searchL.opCount + 1;
			tempMaxL.opCount = tempMaxL.opCount + 1;
			String hashElement = theArray[arrayIndexHash].getTime();
			String stringhashKey = hashElement.replaceAll("[/:.,]|12/2006/", "");
			int inthashKey = Integer.parseInt(stringhashKey); 
			if (inthashKey == intKey) {
				// Found the key so return it
				System.out.print("Search for " +key + " found at index : "+ arrayIndexHash + " ");
				System.out.println(theArray[arrayIndexHash]);
				return theArray[arrayIndexHash];
			}
			// Look in the next index
			++arrayIndexHash;
			// If we get to the end of the array go back to index 0
			arrayIndexHash %= arraySize;
		}
		// Couldn't locate the key
		System.out.println(key + " was not found");
		return null;
	}
	// Returns the value stored in the HashProbe Table
	public static timeStamp searchQuadratic(String key, int setSize, opCount searchQ, opCount tempMaxQ) {
		// Find the keys original HashProbe key
		String stringKey = key.replaceAll("[/:.,]|12/2006/", "");
		int intKey = Integer.parseInt(stringKey);
		int arrayIndexHash = intKey % setSize;
		int tempIndex = arrayIndexHash;
		int factor =0;
		while (theArray[arrayIndexHash].getTime() != "-1") {
			searchQ.opCount = searchQ.opCount + 1;
			tempMaxQ.opCount = tempMaxQ.opCount + 1;			
			String hashElement = theArray[arrayIndexHash].getTime();
			String stringhashKey = hashElement.replaceAll("[/:.,]|12/2006/", "");
			int inthashKey = Integer.parseInt(stringhashKey); 
			if (inthashKey == intKey) {
				// Found the key so return it
				
				System.out.print("Search for " +key + " found at index : "+ arrayIndexHash + " ");
				System.out.println(theArray[arrayIndexHash]);
				return theArray[arrayIndexHash];
			}
			// Look in the next index
			arrayIndexHash = tempIndex + factor^2;
			factor++;
			// If we get to the end of the array go back to index 0
			arrayIndexHash %= arraySize;
		}
		// Couldn't locate the key
		System.out.println(key + " was not found");
		return null;
	}

	HashProbe(int size) {
		arraySize = size;
		theArray = new timeStamp[size];
		Arrays.fill(theArray, new timeStamp("-1","-1","-1"));
	}

	public void displayTheStack(int setSize) {
		int tableSize = setSize/5;
		int increment = 0;
		for (int m = 0; m < tableSize; m++) {
			increment += 5;
			for (int n = increment - 5; n < increment; n++) {
				System.out.format("| %3s " + " ", n);
			}
			System.out.println("|");
			for (int n = increment - 5; n < increment; n++) {
				if ((theArray[n].getTime()).equals("-1")) {
					System.out.print("|      ");
				}
				else {
					System.out.print(String.format("| %3s " + " ", theArray[n].getTime()));
				}
			}
			System.out.println("|");
		}
	}

}
