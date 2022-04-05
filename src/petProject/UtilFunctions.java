package petProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

public class UtilFunctions {

	public static final String[] getKeyValueFromHadoopOutput(Text value) {
		String[] strings;
		String wholeString = new String (value.copyBytes());
		strings = wholeString.split("\\t");
		if(strings.length == 2) {
			return strings;
		}
		return null;
		
	}
	public static final String[] getKeyValueFromHadoopOutput(String string) {
		String[] strings;
		strings = string.split("\\t");
		if(strings.length == 2) {
			return strings;
		}
		return null;
		
	}
	
	
	public static final BufferedReader getBufferedReaderFromFile(String fileName, Configuration configuration) throws URISyntaxException, IOException {
		FileSystem fs = FileSystem.get(configuration);
		Path path = new Path(fileName);
		FSDataInputStream fsDataInputStream = fs.open(path);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fsDataInputStream));
		return bufferedReader;
	}
	
	public static final List<String> getLinesFromFile(String fileName, Configuration configuration){
		List<String> listOfLines = new ArrayList<String>();
		
		BufferedReader bufferedReader = null;
	    try {
	        bufferedReader = UtilFunctions.getBufferedReaderFromFile(fileName,configuration);
	        String line;
	        while ((line = bufferedReader.readLine()) != null) {
	        	if(line.length()>0) {
		            listOfLines.add(line);
	        	}
	        }
	    }
	    catch(IOException ex) {
	    }
	    catch(URISyntaxException ex) {
	        
	    } finally{
	    	if(bufferedReader !=null) {
	    		try {
					bufferedReader.close();
				} catch (IOException e) {
				}
	    	}
	    }
		return listOfLines;
	}
	
	
}
