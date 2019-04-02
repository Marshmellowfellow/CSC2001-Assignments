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

		
		String CSVName = "cleaned_data.csv";
		
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
		
    	if(args.length > 1) {
	  		for(int i = 0; i < (args.length) -1 ; i++) {
	  			if(("-p" .contains(args[i]))) {
	  				if(i < args.length){
		  				if (args[i+1].contains("Q")) {
		  					set = 1;
		  					hashImplementation.quadraticProbe(dataSet, HashProbe.theArray, setSize, insertQ);
		  					//hashImplementation.displayTheStack(setSize);
		  					System.out.println("Quadratic Probe");
		  				}
	  				}
				}
	  		}
	  		
    	}
    	if(set ==0) {
			hashImplementation.linearProbe(dataSet, HashProbe.theArray, setSize, insertL);
			//hashImplementation.displayTheStack(setSize);
			System.out.println("Linear Probe");
    	}

		//Searching through the input arguments and running necessary functionality
    	if(args.length > 0) {
	  		for(int i = 0; i < (args.length) ; i++) {
	  			if(("-s" .contains(args[i]))) {
	  				List<String> keys = KEYread("keys.txt");
	  				if(set == 0) {
		  				for(int j = 0; j<keys.size();j++) {
		  					searchLinear(keys.get(j), setSize, searchL);
		  				}
	  				}else {
		  				for(int j = 0; j<keys.size();j++) {
		  					searchQuadratic(keys.get(j), setSize, searchQ);
		  				}
	  				}
	  			}
//				}else if(("-c" .contains(args[i])) && (args[i+1]) != null ) {
//					hashImplementation.opCount();
//				}
	  			 else if(("-keys" .contains(args[i])) && (args[i+1]) != null ) {
	  				int size = Integer.parseInt(args[i+1]);
	  				hashImplementation.randomKeySet(size);

				}
	  		}
    	}else {
    		for(int i = 0; i<dataSet.size();i++) {
    			//System.out.println(dataSet.get(i));	
    		}
    	}
    	System.out.println("insertQ : " + insertQ.opCount);
    	System.out.println("searchQ : " + searchQ.opCount);
    	System.out.println("insertL : " + insertL.opCount);
    	System.out.println("searchL : " + searchL.opCount);
    	
    	for(int i = 0; i < (args.length) ; i++) {	
	  		if("-test" .equals(args[i])) {
	    		if(set == 1) {
	    			String fileName = ("test/qudratic.txt");
	  				FileWriter fileWriter;
					try {
						fileWriter = new FileWriter(fileName, true);
		  				String text = (setSize + "," + String.valueOf(insertQ.opCount) + "," + String.valueOf(searchQ.opCount));
		  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
		  				textWriter.write(fileWriter, fileName, text);
					} catch (IOException e) {
						e.printStackTrace();
					}
	    		}else {
	  				String fileName = ("test/linear.txt");
	  				FileWriter fileWriter;
					try {
						fileWriter = new FileWriter(fileName, true);
		  				String text = (setSize + "," + String.valueOf(insertL.opCount) + "," + String.valueOf(searchL.opCount));
		  				textWrite textWriter = new textWrite(fileWriter, fileName,  text);
		  				textWriter.write(fileWriter, fileName, text);
					} catch (IOException e) {
						e.printStackTrace();
					}
	    		}	
	  		}
    	}
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

	public static timeStamp searchLinear(String key, int setSize, opCount searchL) {
		// Find the keys original HashProbe key
		String stringKey = key.replaceAll("[/:.,]|12/2006/", "");
		int intKey = Integer.parseInt(stringKey);
		int arrayIndexHash = intKey % setSize;
		while (theArray[arrayIndexHash].getTime() != "-1") {
			searchL.opCount = searchL.opCount + 1;
			
			String hashElement = theArray[arrayIndexHash].getTime();
			String stringhashKey = hashElement.replaceAll("[/:.,]|12/2006/", "");
			int inthashKey = Integer.parseInt(stringhashKey); 
			if (inthashKey == intKey) {
				// Found the key so return it
				System.out.print("Found at index : "+ arrayIndexHash + " ");
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
	public static timeStamp searchQuadratic(String key, int setSize, opCount searchQ) {
		// Find the keys original HashProbe key
		String stringKey = key.replaceAll("[/:.,]|12/2006/", "");
		int intKey = Integer.parseInt(stringKey);
		int arrayIndexHash = intKey % setSize;
		int tempIndex = arrayIndexHash;
		int factor =0;
		while (theArray[arrayIndexHash].getTime() != "-1") {
			searchQ.opCount = searchQ.opCount + 1;
			
			String hashElement = theArray[arrayIndexHash].getTime();
			String stringhashKey = hashElement.replaceAll("[/:.,]|12/2006/", "");
			int inthashKey = Integer.parseInt(stringhashKey); 
			if (inthashKey == intKey) {
				// Found the key so return it
				System.out.print("Found at index : "+ arrayIndexHash + " ");
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
