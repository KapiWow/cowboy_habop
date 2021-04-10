import java.io.*;
import java.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;

public class HadoopDriver extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int ret = ToolRunner.run(new Configuration(), new HadoopDriver(), args);
        System.exit(ret);
    }

    public int run(String[] args) throws Exception {

        if (args.length != 3) {
            ToolRunner.printGenericCommandUsage(System.err);
            System.err.println("USAGE: hadoop jar ... <input-dir> <output-dir> <temp-dir>");
            System.exit(1);
        }

        Job job = Job.getInstance(getConf());
        job.setJarByClass(HadoopDriver.class);
        job.setJobName("WordCounter");


        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        job.setMapperClass(WordMapper.class);
        job.setReducerClass(Summer.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(MyWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        System.out.println("Input dirs: " + Arrays.toString(FileInputFormat.getInputPaths(job)));
        System.out.println("Output dir: " + FileOutputFormat.getOutputPath(job));

        int returnValue = job.waitForCompletion(true) ? 0 : 1;

        System.out.println("First Task Ended");
        
        
        Job job2 = Job.getInstance(getConf());
        job2.setJarByClass(HadoopDriver.class);
        job2.setJobName("Sort");


        FileInputFormat.addInputPath(job2, new Path(args[2]));
        FileOutputFormat.setOutputPath(job2, new Path(args[1]));
        
        job2.setMapperClass(SortMapper.class);
        job2.setReducerClass(SortReducer.class);
        
        job2.setOutputKeyClass(MyWritableComparable.class);
        //job2.setOutputValueClass(IntWritable.class);
        job2.setOutputValueClass(NullWritable.class);
        job2.setOutputFormatClass(TextOutputFormat.class);
        int returnValue2 = job2.waitForCompletion(true) ? 0 : 1;

        return returnValue2;
    }
}
