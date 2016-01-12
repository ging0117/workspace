package FileStreaming;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStreamManager {
	/*
	 * 
	 * 
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileStreamManager obj = new FileStreamManager();

		try {
			obj.createMyFile("Input.txt");
			obj.writeFileContent("Input.txt");
			obj.readFileContent("Input.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void writeFile(String string) {
		// TODO Auto-generated method stub

	}

	/**
	 * @throws IOException
	 * 
	 * 
	 */

	public void createMyFile(String filename) throws IOException {

		File fileObj = new File(filename);
		if (fileObj.exists()) {

			System.out.println("file already exists");
		} else {

			fileObj.createNewFile();
		}

	}

	public void writeFileContent(String FileName) throws IOException {

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			fos = new FileOutputStream(FileName);
			bos = new BufferedOutputStream(fos, 200);

			String content = "11,john, Stattowm,24,94536";
			bos.write(content.getBytes());

			bos.flush();
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
	}

	public void readFileContent(String filename) throws IOException {
		
		 FileInputStream fs=null;
		BufferedInputStream bis=null;
		
		try{
		fs =new FileInputStream(filename);
		bis = new BufferedInputStream(fs,200);
		int data;
	
			
		
		while((data=bis.read())!=-1){
			
			
			System.out.print((char) data);
		}
		}finally{
			if(fs!=null)
			{
		
				fs.close();
			}if(bis!=null){
				
				bis.close();
			}
			
		}
{
		
		fs.close();
		bis.close();
}
	}

}
