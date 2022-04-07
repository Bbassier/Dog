package petProject.hadoop;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import petProject.DecisionNode;
import petProject.JsonToHadoopConverter;
import petProject.LeafNode;
import petProject.UtilFunctions;
import petProject.hadoop.AnimalDaysInShelterCalculator.AnimalMapper;
import petProject.hadoop.AnimalDaysInShelterCalculator.AnimalReducer;
import petProject.pojo.Animal;
import petProject.pojo.FeatureOption;
import petProject.pojo.FeatureOutput;

public class TrainTree {
	
  static Logger logger = Logger.getLogger(TrainTree.class.getName());
  
  public static class AttributeMapper
       extends Mapper<LongWritable, Text, Text, Text>{

		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			Text keyId = new Text();
			Text valueInt = new Text();
    		
    		String animalString = null;
    		
    		String[] keyValuePair = UtilFunctions.getKeyValueFromHadoopOutput(value);
    		if(keyValuePair != null) {
    			animalString = keyValuePair[1];
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
					if(animal.getAttributes() != null && animal.getAttributes().getSpayedNeutered() != null) {
						keyId.set("Spayed");
						valueInt.set(animal.getAttributes().getSpayedNeutered()+":"+animal.getDaysToAdopt());
		        		context.write(keyId, valueInt);
					}
					if(animal.getAttributes() != null && animal.getAttributes().getHouseTrained() != null) {
						keyId.set("HouseTrained");
						valueInt.set(animal.getAttributes().getHouseTrained()+":"+animal.getDaysToAdopt());
		        		context.write(keyId, valueInt);
					}
					if(animal.getAttributes() != null && animal.getAttributes().getSpecialNeeds() != null) {
						keyId.set("SpecialNeeds");
						valueInt.set(animal.getAttributes().getSpecialNeeds()+":"+animal.getDaysToAdopt());
		        		context.write(keyId, valueInt);
					}
					if(animal.getAttributes() != null && animal.getAttributes().getShotsCurrent() != null) {
						keyId.set("Shots");
						valueInt.set(animal.getAttributes().getShotsCurrent()+":"+animal.getDaysToAdopt());
		        		context.write(keyId, valueInt);
					}
					if(animal.getGender() != null ) {
						keyId.set("Gemder");
						valueInt.set(animal.getGender()+":"+animal.getDaysToAdopt());
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
    		ObjectMapper mapper = new ObjectMapper();
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
				FeatureOutput featureOutput = new FeatureOutput();
				featureOutput.setFeatureName(keyString);
				List<FeatureOption> featureOptions = new ArrayList<>();
				int totalCount=0;
				double weightedStandardDeviation = 0.0;
				for(String option:setOfString) {
					FeatureOption featureOption = new FeatureOption();
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
			        
			        featureOption.setName(option);
			        featureOption.setTotalCount(count);
			        featureOption.setStandardDev(res);
			        featureOption.setAverage(mean);
			        
			        featureOptions.add(featureOption);
			       
			        weightedStandardDeviation = weightedStandardDeviation+ weightedRes;
				}
				weightedStandardDeviation = weightedStandardDeviation/totalCount;
				
				featureOutput.setStandardDev(weightedStandardDeviation);
				featureOutput.setTotalCount(totalCount);
				featureOutput.setListOfFeautreOptions(featureOptions);
				
				
				String outputString = mapper.writeValueAsString(featureOutput);;
				
				result.set(outputString);
				context.write(key, result);
			}
		}
  }

  public static void main(String[] args) throws Exception {
	  
	//Convert Single Json into one file of aniamls
    Configuration conf = new Configuration(); 
    logger.info("Converting Json");
    JsonToHadoopConverter jsonToHadoopConverter =  new JsonToHadoopConverter();
	FileSystem fs = FileSystem.get(conf);
	jsonToHadoopConverter.convert(UtilFunctions.getBufferedWriterForFile("jsonTransformed/output.txt", conf), fs);
	logger.info("Converted Json, Moving to Hadoop Job 1");
    conf.setInt("mapreduce.task.io.sort.mb", 2000);
    //Makes each animal JSON on a single line in the file jsonTransformed/output.txt
    
    //Determine how long it took for each dog to get adopted
    Job job1 = Job.getInstance(conf, "Animal Adoptaion Day");
	FileInputFormat.addInputPath(job1, new Path("jsonTransformed"));
	FileOutputFormat.setOutputPath(job1, new Path("output"));
    job1.setJarByClass(AnimalDaysInShelterCalculator.class);
    job1.setMapperClass(AnimalMapper.class);
    job1.setReducerClass(AnimalReducer.class);
    job1.setOutputKeyClass(Text.class);
    job1.setOutputValueClass(Text.class);
    job1.waitForCompletion(true);
    //Only one Instance of JSON per animal is outputed, with the number of days it took to adopt calculated
    
    //Get The features, and there standard devations from pets
    Job job2 = Job.getInstance(conf, "Get options for each atrribute");
	FileInputFormat.addInputPath(job2, new Path("output"));
	FileOutputFormat.setOutputPath(job2, new Path("standardDeviation"));
    job2.setJarByClass(TrainTree.class);
    job2.setMapperClass(AttributeMapper.class);
    job2.setReducerClass(AttributeReducer.class);
    job2.setOutputKeyClass(Text.class);
    job2.setOutputValueClass(Text.class);
    boolean jobComplete =job2.waitForCompletion(true);
    
    //Determine the best Feature to make root Node
    List<String> outputLines = UtilFunctions.getLinesFromFile("standardDeviation/part-r-00000", conf);
    double lowestDevation = Double.MAX_VALUE;
    FeatureOutput bestOutput = null;
    for(String line:outputLines) {
    	String[] keyValue = UtilFunctions.getKeyValueFromHadoopOutput(line);

    	if(keyValue != null) {
    		String value = keyValue[1];
    		ObjectMapper mapper = new ObjectMapper();
    		FeatureOutput output = mapper.readValue(value, FeatureOutput.class);
    		if(output.getStandardDev()<lowestDevation) {
    			lowestDevation =output.getStandardDev();
    			bestOutput = output;
    		}
    		
    	}
    	
    }
    
    //Create Decision Tree
    DecisionNode firstNode = new DecisionNode();
    firstNode.setNodeId(UUID.randomUUID());
    firstNode.setAttributeName(bestOutput.getFeatureName());
    List<String> listOfValues = new ArrayList<>();
    List<LeafNode> listofNodes = new ArrayList<>();
    for(FeatureOption options:bestOutput.getListOfFeautreOptions()) {
    	listOfValues.add(options.getName());
    	LeafNode leaf = new LeafNode();
    	leaf.setNodeId(UUID.randomUUID());
    	leaf.setPredictedValue(options.getAverage());
    	leaf.setAttributeValue(options.getName());
    	listofNodes.add(leaf);
    	
    }
    firstNode.setAcceptedValues(listOfValues);
    firstNode.setChildren(listofNodes);
    
    //Write Tree To File
	ObjectMapper mapper = new ObjectMapper();
    String jsonInString = mapper.writeValueAsString(firstNode);
	BufferedWriter writer = UtilFunctions.getBufferedWriterForFile("desisionTree/firstPass.json", conf);
	writer.write(jsonInString);
	writer.close();
	
    System.exit(jobComplete ? 0 : 1);
    
    
  }
}