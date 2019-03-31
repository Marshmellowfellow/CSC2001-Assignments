import java.util.List;

public class Hash {
	
  public static void main(String[] args) {
		String CSVName = "cleaned_data.csv";
		CSVread dataArray = new CSVread(CSVName);
		List<timeStamp> dataSet = dataArray.read();
		
		for(int i = 0; i<dataSet.size();i++) {
			System.out.println(dataSet.get(i));	
		}
		
  }
	
}
