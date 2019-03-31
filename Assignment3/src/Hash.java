import java.util.Arrays;
import java.util.List;

public class Hash {
	
	timeStamp[] theArray;
	int arraySize;
	int itemsInArray = 0;
	
	public static void main(String[] args) {
		String CSVName = "cleaned_data.csv";
		
		int setSize = 500;
//    	if(args.length > 0) {
//	  		for(int i = 0; i < (args.length) ; i++) {
//	  			if("-l" .contains(args[i])) {
//	  				int j = args.length;
//	  				if((i +1) < j) {
//		  					setSize = Integer.valueOf(args[i + 1]);
//	  				}
//	  			}
//	  		}
//    	}
		
		CSVread dataArray = new CSVread(CSVName, setSize);
		List<timeStamp> dataSet = dataArray.read();
		Hash theFunc = new Hash(setSize);
	  		
		
//		Used to check if data set correctly copied
//		for(int i = 0; i<dataSet.size();i++) {
//			System.out.println(dataSet.get(i));	
//		}

		theFunc.hashFunction(dataSet, theFunc.theArray);
		//theFunc.displayTheStack(setSize);
		
		System.out.println(theFunc.findKey("17003000"));
		System.out.println(theFunc.findKey("17000900"));
		
	}

	// The goal is to make the array big enough to avoid
	// collisions, but not so big that we waste memory
	public void hashFunction(List<timeStamp> dataSet, timeStamp[] theArray) {
		for (int n = 1; n < dataSet.size(); n++) {
			timeStamp newElementVal = ((dataSet.get(n)));
			// Create an index to store the value in by taking the modulus
			System.out.println(newElementVal);
			int arrayIndex = Integer.parseInt((newElementVal.getTime()).replaceAll("[/:.,]|12/2006/", "")) % 499;
			System.out.println("Modulus Index= " + arrayIndex + " for value "+ newElementVal);
			// Cycle through the array until we find an empty space
			while (theArray[arrayIndex].getTime() != "-1") {
				++arrayIndex;
				System.out.println("Collision Try " + arrayIndex + " Instead");
				// If we get to the end of the array go back to index 0
				arrayIndex %= arraySize;
			}
			theArray[arrayIndex] = newElementVal;
		}
	}

	// Returns the value stored in the Hash Table
	public timeStamp findKey(String key) {
		// Find the keys original hash key
		int intKey = Integer.parseInt(key);
		int arrayIndexHash = intKey % 499;
		while (theArray[arrayIndexHash].getTime() != "-1") {
			
			String hashElement = theArray[arrayIndexHash].getTime();
			String stringhashKey = hashElement.replaceAll("[/:.,]|12/2006/", "");
			int inthashKey = Integer.parseInt(stringhashKey); 
			if (inthashKey == intKey) {
				// Found the key so return it
				System.out.println(key + " was found in index "+ arrayIndexHash);
				return theArray[arrayIndexHash];
			}
			// Look in the next index
			++arrayIndexHash;
			// If we get to the end of the array go back to index 0
			arrayIndexHash %= arraySize;
		}
		// Couldn't locate the key
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
					System.out.print(String.format("| %3s " + " ", theArray[n]));
				}
			}
			System.out.println("|");
		}
	}
}
