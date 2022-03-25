package petProject;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

import petProject.pojo.Animal;
import petProject.pojo.AnimalCollection;

public class JsonToHadoopConverter {
	
	public void convert() {
		System.out.println("Converting Json To Output");
	    ObjectMapper mapper = new ObjectMapper();
	    File inputs;
	    FileWriter fileWriter;
	    PrintWriter printWriter = null;
		try {
			//Opens an output file to write into called Output.txt
		    fileWriter = new FileWriter("Output.txt");
		    printWriter = new PrintWriter(fileWriter);
		    //Looks on class path to find folder called inputs
			inputs = new File(MainRunner.class.getClassLoader().getResource("inputs").toURI());
			File[] directoryListing = inputs.listFiles();
			  if (directoryListing != null) {
				  //Gets all folders in inputs, expects it to be named in mmdd format
			    for (File child : directoryListing) {
			    	//Gets all json in folder named mmdd
			    	for(File json:child.listFiles()) {
						  // Convert JSON file to Java objects using Jackson Mapper
			            AnimalCollection animalCollection = mapper.readValue(json, AnimalCollection.class);
			            for (Animal animal:animalCollection.getAnimals()) {
			            	//Print indivial animal on single line in output file.
			            	printWriter.println(animal.toString() +",Date="+child.getName());
			            }	
			    	}
			    }
			  } 
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if(printWriter !=null) {
				 printWriter.close();
			}
		}
	}

}
