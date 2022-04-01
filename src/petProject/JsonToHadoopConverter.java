package petProject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import petProject.pojo.Animal;
import petProject.pojo.AnimalCollection;
import petProject.pojo.FileWithDate;

public class JsonToHadoopConverter {

	public void convert() {
		System.out.println("Converting Json To Output");
		ObjectMapper mapper = new ObjectMapper();
		try (LimitedFileWriter outFile = new LimitedFileWriter("output", 3, 10000000)){
			List<FileWithDate> jsonFiles = getJsonFiles();
			for (FileWithDate json : jsonFiles) {
				// Convert JSON file to Java objects using Jackson Mapper
				AnimalCollection animalCollection = mapper.readValue(json.getFile(), AnimalCollection.class);
				for (Animal animal : animalCollection.getAnimals()) {
					// Print indivial animal on single line in output file.
					if(animal.getSpecies().equalsIgnoreCase("Dog")) {
						animal.setDateAccessed(json.getDate());
						String jsonInString = mapper.writeValueAsString(animal);
						outFile.writeLine(jsonInString);

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private List<FileWithDate> getJsonFiles() throws URISyntaxException {
		// Looks on class path to find folder called inputs
		File inputs = new File(MainRunner.class.getClassLoader().getResource("inputs").toURI());
		List<FileWithDate> jsonFiles = new ArrayList<FileWithDate>();
		File[] directoryListing = inputs.listFiles();
		if (directoryListing != null) {
			// Gets all folders in inputs, expects it to be named in mmdd format
			for (File child : directoryListing) {
				if (child.isDirectory()) {
					for (File json : child.listFiles()) {
						FileWithDate fileWithDate = new FileWithDate();
						fileWithDate.setDate(child.getName());
						fileWithDate.setFile(json);
						jsonFiles.add(fileWithDate);
					}
				}
			}
		}
		return jsonFiles;
	};

}
