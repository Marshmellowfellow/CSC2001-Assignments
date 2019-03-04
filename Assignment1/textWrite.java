import java.io.FileWriter;
import java.io.IOException;

public class textWrite {
	public String filename;
	public String text;
	public FileWriter writer;
	
	textWrite(FileWriter writer, String filename, String text){
		this.filename = filename;
		this.text = text;
		this.writer = writer;
	}
	
	public void write(FileWriter writer, String fileName, String text) {
		try {
			writer.write(text);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
