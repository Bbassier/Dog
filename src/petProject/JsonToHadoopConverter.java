package petProject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import com.fasterxml.jackson.databind.ObjectMapper;

import petProject.pojo.Animal;
import petProject.pojo.AnimalCollection;
import petProject.pojo.AnimalCollectionWithDate;

public class JsonToHadoopConverter {

	public void convert(BufferedWriter writer, FileSystem fs) {
		System.out.println("Converting Json To Output");
		ObjectMapper mapper = new ObjectMapper();
		try (LimitedFileWriter outFile = new LimitedFileWriter("output", 3, 10000000)){
			List<AnimalCollectionWithDate> jsonFiles = getJsonFiles(fs);
			for (AnimalCollectionWithDate json : jsonFiles) {
				// Convert JSON file to Java objects using Jackson Mapper
				for (Animal animal : json.getAnimalCollection().getAnimals()) {
					// Print indivial animal on single line in output file.
					if(animal.getSpecies().equalsIgnoreCase("Dog")) {
						animal.setDateAccessed(json.getDate());
						String jsonInString = mapper.writeValueAsString(animal);
						writer.write(jsonInString);
						writer.newLine();
					}
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private List<AnimalCollectionWithDate> getJsonFiles(FileSystem fs) throws URISyntaxException, IOException {
		// Looks on class path to find folder called inputs
		Path jsonInputs = new Path("jsonInputs");
		boolean exsists = fs.exists(jsonInputs);
		ObjectMapper mapper = new ObjectMapper();
		RemoteIterator<LocatedFileStatus> byDayFolders = fs.listLocatedStatus(jsonInputs);
		List<AnimalCollectionWithDate> jsonFiles = new ArrayList<AnimalCollectionWithDate>();
		if (byDayFolders != null && exsists) {
			// Gets all folders in inputs, expects it to be named in mmdd format
			while (byDayFolders.hasNext()) {
				LocatedFileStatus dayDirectory = byDayFolders.next();
				if (dayDirectory.isDirectory()) {
					RemoteIterator<LocatedFileStatus> jsonForDayList = fs.listFiles(dayDirectory.getPath(), false);
					while(jsonForDayList.hasNext()) {
						LocatedFileStatus jsonForDay = jsonForDayList.next();
						AnimalCollectionWithDate fileWithDate = new AnimalCollectionWithDate();
						Path pathToJson = jsonForDay.getPath();
						FSDataInputStream dataStream = fs.open(pathToJson);
						fileWithDate.setDate(dayDirectory.getPath().getName());
						AnimalCollection animalCollection = mapper.readValue(dataStream.readAllBytes(), AnimalCollection.class);
						fileWithDate.setAnimalCollection(animalCollection);
						dataStream.close();
						jsonFiles.add(fileWithDate);
					}
				}
			}

		}
		return jsonFiles;
	};

}
