import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class HashChain {

	static dataList[] theArray;
	static int arraySize;
	int itemsInArray = 0;
	public List<timeStamp> elementsToAdd;

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
        System.out.println("Set size = " + setSize);
        
        //Reading the CSV
		CSVread dataArray = new CSVread(CSVName, setSize);
		List<timeStamp> dataSet = dataArray.read();
		
		
		
		
		HashChain hashTable = new HashChain(dataSet, setSize);
		//hashTable.displayTheArray();
		
		
		if(args.length > 0) {
			for(int i = 0; i < (args.length) ; i++) {
				if(("-s" .contains(args[i]))) {
	  				List<String> keys = KEYread("keys.txt");
	  				for(int j = 0; j<keys.size();j++) {
	  					String stringKey = (keys.get(j)).replaceAll("[/:.,]|12/2006/", "");
	  					int intKey = Integer.parseInt(stringKey);
	  					System.out.println(find(intKey));
	  				}
				}
				//else if(("-c" .contains(args[i])) && (args[i+1]) != null ) {
//					hashImplementation.opCount();
//				}
	  			 else if(("-k" .contains(args[i])) && (args[i+1]) != null ) {
	  				int size = Integer.parseInt(args[i+1]);
	  				ArrayList<String> randomKeys = hashTable.randomKeySet(size);
	  				//System.out.println(randomKeys);
	  				for(int j = 0; j<size;j++) {
	  					String stringKey = (randomKeys.get(j)).replaceAll("[/:.,]|12/2006/", "");
	  					int intKey = Integer.parseInt(stringKey);
	  					System.out.println(find(intKey));
	  				}
				}
	  		}
    	}else {
    		for(int i = 0; i<dataSet.size();i++) {
    			System.out.println(dataSet.get(i));	
    		}
    	}
	}

	HashChain(List<timeStamp> elements,int size) {

		this.arraySize = size;
		this.elementsToAdd = elements;
		theArray = new dataList[size];

		// Fill the array with WordLists
		for (int i = 0; i < arraySize; i++) {

			theArray[i] = new dataList();
		}
		addTheArray(elementsToAdd);

	}

	public ArrayList<String> randomKeySet(int size){
		String CSVName = "cleaned_data.csv";
		CSVread dataArray = new CSVread(CSVName, 499);
		List<timeStamp> dataSet = dataArray.read();
		Collections.shuffle(dataSet);
		ArrayList<String> keyList = new ArrayList<String>();
		for(int i = 0;i< size;i++) {
			keyList.add((dataSet.get(i)).getTime());
			//searching for each key in the keyset
		}
		return keyList;
	}

	public void insert(timeData newWord) {

		timeStamp dataToHash = newWord.timeStampData;

		// Calculate the hashkey for the timeData

		int hashKey = Integer.parseInt((dataToHash.getTime()).replaceAll("[/:.,]|12/2006/", "")) % arraySize;

		// Add the new timeData to the array and set
		// the key for the timeData

		theArray[hashKey].insert(newWord, hashKey);

	}

	public static timeData find(int dataToFind) {

		// Calculate the hash key for the timeData

		int hashKey = dataToFind % arraySize;

		// NEW

		timeData timeStampData = theArray[hashKey].find(dataToFind, arraySize);

		return timeStampData;

	}

	public void addTheArray(List<timeStamp> elementsToAdd) {

		for (int i = 1; i < elementsToAdd.size(); i++) {
			timeStamp timestampData = elementsToAdd.get(i);
			// Create the timeData with the timeData name and
			// intKey-

			timeData newWord = new timeData(timestampData, arraySize);
			// Add the timeData to theArray
			insert(newWord);
		}

	}

	public void displayTheArray() {

		for (int i = 0; i < arraySize; i++) {

			System.out.println("theArray Index " + i);

			theArray[i].displaytimeData();

		}

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



class timeData {

	public timeStamp timeStampData;
	public int intKey;
	int arraySize;

	public int key;

	// Reference to next timeData made in the dataList

	public timeData next;

	public timeData(timeStamp timeStampData, int arraySize) {

		this.arraySize = arraySize;
		this.timeStampData = timeStampData;
		this.intKey = Integer.parseInt((timeStampData.getTime()).replaceAll("[/:.,]|12/2006/", "")) % arraySize;

	}

	public String toString() {

		return "Found at index : " + intKey +" "+ timeStampData;

	}

}

class dataList {

	// Reference to first Link in list
	// The last Link added to the LinkedList

	public timeData firstData = null;

	public void insert(timeData newWord, int hashKey) {

		timeData previous = null;
		timeData current = firstData;

		newWord.key = hashKey;

		while (current != null && newWord.key > current.key) {

			previous = current; 
			current = current.next;

		}

		if (previous == null)
			firstData = newWord;

		else
			previous.next = newWord;

		newWord.next = current;

	}

	public void displaytimeData() {

		timeData current = firstData;

		while (current != null) {

			System.out.println(current);
			current = current.next;

		}

	}

	public timeData find(int dataToFind, int arraySize) {

		timeData current = firstData;

		// Search for key, but stop searching if
		// the hashKey < what we are searching for
		// Because the list is sorted this allows
		// us to avoid searching the whole list
		int hashKey = dataToFind % arraySize;;
		while (current != null && current.key <= hashKey) {

			// NEW
			if (Integer.parseInt((current.timeStampData).getTime().replaceAll("[/:.,]|12/2006/", "")) == dataToFind)
				return current;

			current = current.next;

		}

		return null;

	}

}