import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * 
 * @author ging0117
 *
 */
public class FileReaderManager {
/**
 * 
 * 
 * @param args
 * @throws IOException 
 */
public void readTextFile(String filename) throws IOException{
FileReader reader = null;
BufferedReader buf= null;
try{
 reader = new FileReader(filename);
 buf= new BufferedReader(reader,200);
 
// String data= buf.readLine();
String data ="";
while((data=buf.readLine())!=null){
	
	System.out.println(data);
	String[]orr = data.split(",");
	int age =Integer.parseInt(orr[3].trim());
	if(age>30){
		
		System.out.println("True");
	}else{
		
		System.out.println("False");
	}
	
}
}finally{
	if(reader !=null)
	{
	reader.close();
	}
	if(buf!=null)
	{
		buf.close();	
		
	}
}
 
 
 
 
}
	
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
FileReaderManager obj = new FileReaderManager();
obj.readTextFile("Input.txt");
		
	
	}

}
