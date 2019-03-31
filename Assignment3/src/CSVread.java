import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVread{

  public String FileName;
  public List<timeStamp> dataSet;

  public CSVread(String FileName){
	   this.FileName = FileName;
  }
  public List<timeStamp> read(){
	   dataSet = new ArrayList<>();
	   String line = "";
	   int lineNo = 0;
	   try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {
	       while ((line = br.readLine()) != null) {
			   	if(lineNo > 0) {
			           String[] Element = line.split(",");
			           dataSet.add(new timeStamp(Element[3],Element[1],Element[0]));
				}
			   	lineNo ++;
	       }
	   } 
   catch (IOException e) {
       e.printStackTrace();
   }
   return dataSet;
  }
}
	
