package petProject.hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

import petProject.pojo.Animal;

public class HadoopRunner2 {

  public static class AttributeMapper
       extends Mapper<LongWritable, Text, Text, Text>{

		

		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			Text keyId = new Text();
			Text valueInt = new Text();
    		String wholeString = new String (value.copyBytes());
    		String[] stringArray = new String[2];
    		stringArray = wholeString.split("\\t");
    		String animalString = null;
    		if(stringArray.length==2) {
    			animalString = stringArray[1];
    		}
    		ObjectMapper mapper = new ObjectMapper();
			if (animalString != null && !animalString.isEmpty()) {
				try {
					Animal animal = mapper.readValue(animalString, Animal.class);
					
					if(animal.getAge() != null) {
						keyId.set("Age");
						valueInt.set(animal.getAge()+":"+animal.getDaysToAdopt());
		        		context.write(keyId, valueInt);
					}
					if(animal.getBreeds() != null && animal.getBreeds().getPrimary() != null) {
						keyId.set("BreedPrimary");
						valueInt.set(animal.getBreeds().getPrimary()+":"+animal.getDaysToAdopt());
		        		context.write(keyId, valueInt);
					}
					if(animal.getSize() != null) {
						keyId.set("Size");
						valueInt.set(animal.getSize()+":"+animal.getDaysToAdopt());
		        		context.write(keyId, valueInt);
					}
					if(animal.getCoat() != null) {
						keyId.set("Age");
						valueInt.set(animal.getCoat()+":"+animal.getDaysToAdopt());
		        		context.write(keyId, valueInt);
					}
					if(animal.getColors() != null && animal.getColors().getPrimary() != null) {
						keyId.set("Color");
						valueInt.set(animal.getColors().getPrimary()+":"+animal.getDaysToAdopt());
		        		context.write(keyId, valueInt);
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
				} 
				
			}
		}
  }

  public static class AttributeReducer
       extends Reducer<Text,Text,Text,Text> {
	  private Text result = new Text();
		public static final int lastDayOfRecords = 0314;

		@Override
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			Set<String> setOfString = new HashSet<String>();
			Map<String, Integer> mapOfCount = new HashMap<String,Integer>();
			Map<String, Integer> mapOfSum = new HashMap<String,Integer>();
			Map<String, List<Integer>> mapOfList = new HashMap<String,List<Integer>>();
			String keyString = key.toString();
			for (Text val : values) {
				String valString = val.toString();
				String[] arrayOfStrings = valString.split(":");
				String feature = arrayOfStrings[0];
				String days = arrayOfStrings[1];
				Integer currentCount = mapOfCount.get(feature);
				if(currentCount ==null) {
					mapOfCount.put(feature, 1);
				}
				else {
					mapOfCount.put(feature, currentCount+1);
				}
				
				Integer currentSum = mapOfSum.get(feature);
				if(currentSum ==null) {
					mapOfSum.put(feature, Integer.parseInt(days));
				}
				else {
					mapOfSum.put(feature, currentSum+Integer.parseInt(days));
				}
				
				List<Integer> currentList = mapOfList.get(feature);
				if(currentList ==null) {
					currentList = new ArrayList<>();
					currentList.add(Integer.parseInt(days));
					mapOfList.put(feature, currentList);
				}
				else {
					currentList.add(Integer.parseInt(days));
					mapOfList.put(feature, currentList);
				}
				
				setOfString.add(feature);
				
			}
			if(keyString !=null && !keyString.isEmpty()) {
				String outputString = "";
				int totalCount=0;
				double weightedStandardDeviation = 0.0;
				for(String option:setOfString) {
					
					int sum = mapOfSum.get(option);
					int count = mapOfCount.get(option);
					totalCount =totalCount+count;
					double mean = sum/count;
					double standardDeviation = 0.0;
					double res = 0.0;
				    double sq = 0.0;
					List<Integer> list = mapOfList.get(option);
					for(Integer el:list) {
						standardDeviation
		                = standardDeviation + Math.pow((el - mean), 2);
					}
					sq = standardDeviation / count;
			        res = Math.sqrt(sq);
			        double weightedRes = res*count;
			        weightedStandardDeviation = weightedStandardDeviation+ weightedRes;
					outputString = outputString +option+":"+res+",";
				}
				weightedStandardDeviation = weightedStandardDeviation/totalCount;
				outputString = outputString + "Weight SD:"+weightedStandardDeviation;
				
				result.set(outputString);
				context.write(key, result);
			}
		}
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    conf.setInt("mapreduce.task.io.sort.mb", 2000);
    Job job = Job.getInstance(conf, "Get options for each atrribute");
	FileInputFormat.addInputPath(job, new Path("output"));
	FileOutputFormat.setOutputPath(job, new Path("standardDeviation"));
    job.setJarByClass(HadoopRunner2.class);
    job.setMapperClass(AttributeMapper.class);
    job.setReducerClass(AttributeReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}