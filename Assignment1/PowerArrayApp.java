import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class PowerArrayApp {
	
    public static void main(String[] args) {
    	String CSVName = "/home/marshmewllow/Desktop/Engineering/2019/CSC2001F/MyRepo/Assignment1/cleaned_data.csv";
    	CSVread(CSVName);

    }
    
    public class timeStamp{
    	
    }
    
   public static void CSVread(String FileName){
       String line = "";
       try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {

    	   int lineNo = 0;
           while ((line = br.readLine()) != null) {
               String[] Element = line.split(",");
               
               int i = 0;
               String value = "";
               int size = Element.length;
               while(i < size) {
            	   	if (lineNo > 0) {
            	   		if (i == 0) {
            	   			System.out.print(Element[0] + "   ");
                	   		i ++;	
            	   		}
            	   		else {
            	   			value = (value + Element[i] + "/");
                	   		i ++;	
            	   		}
        	   			
            	   	}
            	   	else {
            	   		i ++;
            	   	}
               }
               System.out.println(value);
               lineNo ++;
           }

       } catch (IOException e) {
           e.printStackTrace();
       }
		
   }
}






