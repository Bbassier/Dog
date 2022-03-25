package petProject.hadoop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HadoopRunner {

  public static class AnimalMapper
       extends Mapper<LongWritable, Text, Text, Text>{

		private Text keyId = new Text();
		private Text valueInt = new Text();

		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			Map<String, String> keyValueMap = new HashMap<String, String>();
			while (itr.hasMoreTokens()) {
				String text = itr.nextToken();
				String[] textValues = text.split(",");
				for (String txt : textValues) {
					String[] keyValue = txt.split("=");
					if (keyValue.length == 2) {
						keyValueMap.put(keyValue[0], keyValue[1]);
					}
					
				}
				String idAsString = keyValueMap.get("id");
				if(idAsString!=null && !idAsString.isEmpty()) {
					keyId.set(idAsString);
				}
				else {
					keyId.set("-1");
				}
				String date = keyValueMap.get("Date");
				if(date!=null && !date.isEmpty()) {
					valueInt.set(date);
				}
				else {
					valueInt.set("-1");
				}
				context.write(keyId, valueInt);
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
			boolean wasThereOn12 = false;
			boolean wasThereOn13 = false;
			boolean wasThereOn14 = false;
			String adoptedOrNot;
			for (Text val : values) {
				int intOfVal = Integer.parseInt(val.toString());
				if(intOfVal == 314) {
					wasThereOn14 = true;
				}
				if(intOfVal == 313) {
					wasThereOn13 = true;
				}
				if(intOfVal == 312) {
					wasThereOn12 = true;
				}
			}
			if((wasThereOn12 || wasThereOn13) && !wasThereOn14) {
				adoptedOrNot = " Adopted on : ";
				if(wasThereOn13) {
					adoptedOrNot = adoptedOrNot+"03 13";
				}
				else {
					adoptedOrNot = adoptedOrNot+"03 12";
				}
			}
			else {
				adoptedOrNot = " Not Adopted";
			}
			result.set(adoptedOrNot);
			context.write(key, result);
		}
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "Animal Adoptaion Day");
	FileInputFormat.addInputPath(job, new Path("input"));
	FileOutputFormat.setOutputPath(job, new Path("output"));
    job.setJarByClass(HadoopRunner.class);
    job.setMapperClass(AnimalMapper.class);
    job.setReducerClass(AnimalReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}