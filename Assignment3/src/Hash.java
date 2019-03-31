import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Hash {
	
	timeStamp[] theArray;
	int arraySize;
	int itemsInArray = 0;
	
	public static void main(String[] args) {
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
		if(setSize>500){setSize = isPrime(500);}
        System.out.println("Set size = " + setSize);
        
        //Reading the CSV
		CSVread dataArray = new CSVread(CSVName, setSize);
		List<timeStamp> dataSet = dataArray.read();
		Hash hashImplementation = new Hash(setSize);



		//Building the table
		hashImplementation.hashFunction(dataSet, hashImplementation.theArray);
		
		//Display table
		// hashImplementation.displayTheStack(setSize);

		//Searching through the input arguments and running necessary functionality
    	if(args.length > 1) {
	  		for(int i = 0; i < (args.length) ; i++) {
	  			if(("-s" .contains(args[i])) && (args[i+1]) != null ) {
	  				hashImplementation.search(args[i+1]);
	  			}
//				}else if(("-c" .contains(args[i])) && (args[i+1]) != null ) {
//					hashImplementation.opCount();
//				}
	  		else if(("-k" .contains(args[i])) && (args[i+1]) != null ) {
	  				int size = Integer.parseInt(args[i+1]);
	  				ArrayList<String> randomKeys = hashImplementation.keysetkeys(dataSet,size);
	  				//System.out.println(randomKeys);
				}
	  		}
    	}else {
    		for(int i = 0; i<dataSet.size();i++) {
    			System.out.println(dataSet.get(i));	
    		}
    	}
	}
	
	
	
	public ArrayList<String> keysetkeys(List<timeStamp> dataSet, int setSize){
		Collections.shuffle(dataSet);
		ArrayList<String> keyList = new ArrayList<String>();
		for(int i = 0;i< setSize;i++) {
			keyList.add((dataSet.get(i)).getTime());
			//searching for each key in the keyset
			search((dataSet.get(i)).getTime());
		}
		return keyList;
	}
	
	public static int isPrime(int setSize) {
        int prime = 0;  //next prime will be assigned to this var
        for(int j = setSize; j<1000; j++){  //outer loop

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
	public void hashFunction(List<timeStamp> dataSet, timeStamp[] theArray) {
		for (int n = 1; n < dataSet.size(); n++) {
			timeStamp newElementVal = ((dataSet.get(n)));
			// Create an index to store the value in by taking the modulus
			//System.out.println(newElementVal);
			int arrayIndex = Integer.parseInt((newElementVal.getTime()).replaceAll("[/:.,]|12/2006/", "")) % 499;
			//System.out.println("Modulus Index= " + arrayIndex + " for value "+ newElementVal);
			// Cycle through the array until we find an empty space
			while (theArray[arrayIndex].getTime() != "-1") {
				++arrayIndex;
				//System.out.println("Collision Try " + arrayIndex + " Instead");
				// If we get to the end of the array go back to index 0
				arrayIndex %= arraySize;
			}
			theArray[arrayIndex] = newElementVal;
		}
	}

	// Returns the value stored in the Hash Table
	public timeStamp search(String key) {
		// Find the keys original hash key
		String stringKey = key.replaceAll("[/:.,]|12/2006/", "");
		int intKey = Integer.parseInt(stringKey);
		int arrayIndexHash = intKey % 499;
		while (theArray[arrayIndexHash].getTime() != "-1") {
			
			String hashElement = theArray[arrayIndexHash].getTime();
			String stringhashKey = hashElement.replaceAll("[/:.,]|12/2006/", "");
			int inthashKey = Integer.parseInt(stringhashKey); 
			if (inthashKey == intKey) {
				// Found the key so return it
				System.out.println(key + " was found in index "+ arrayIndexHash);
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

	Hash(int size) {
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
