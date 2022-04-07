package petProject.hadoop;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import com.fasterxml.jackson.databind.ObjectMapper;

import petProject.pojo.Animal;

public class AnimalDaysInShelterCalculator {

  public static class AnimalMapper
       extends Mapper<LongWritable, Text, Text, Text>{

		

		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    		String animalString = new String (value.copyBytes());
    		ObjectMapper mapper = new ObjectMapper();
			if (animalString != null && !animalString.isEmpty()) {
				try {
					Animal animal = mapper.readValue(animalString, Animal.class);
					Text keyId = new Text();
					Text valueInt = new Text();
					keyId.set(animal.getId().toString());
					valueInt.set(animalString);
	        		context.write(keyId, valueInt);
				}
				catch (Exception e) {
					e.printStackTrace();
				} 
				
			}
		}
  }

  public static class AnimalReducer
       extends Reducer<Text,Text,Text,Text> {
	  private Text result = new Text();
		public static final int lastDayOfRecords = 0314;

		@Override
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			ObjectMapper mapper = new ObjectMapper();
			Integer lastDayIn = null;
			LocalDate firstDayInDate;
			LocalDate lastDayInDate;
			Animal animal = null; 
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'+'SSSS");
			for (Text val : values) {
				animal = mapper.readValue(val.toString(), Animal.class);
				Integer dateAccessed = Integer.parseInt(animal.getDateAccessed());
				if(lastDayIn == null) {
					lastDayIn = dateAccessed;
				}

				if(lastDayIn < dateAccessed) {
					lastDayIn = dateAccessed; 
				}
			}
			if(animal !=null && lastDayIn < 316) { 
				lastDayInDate = LocalDate.parse("0"+lastDayIn.toString()+"2022",formatter);
				firstDayInDate = LocalDate.parse(animal.getStatusChangedAt(),formatter2);
				Duration duration = Duration.between(firstDayInDate.atStartOfDay(),lastDayInDate.atStartOfDay());
				animal.setDaysToAdopt((int) duration.toDays());
				
			}
			if(animal !=null && animal.getDaysToAdopt() != null) {
        		String jsonInString = mapper.writeValueAsString(animal);
				result.set(jsonInString);
				context.write(key, result);
			}
		}
  }
}