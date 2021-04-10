import java.io.*;
import java.util.*;
import java.lang.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;

public class Summer extends Reducer<Text, IntWritable, Text, IntWritable> {
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
        throws IOException, InterruptedException {
        int sum = 0;
        Iterator<IntWritable> valuesIt = values.iterator();

        while(valuesIt.hasNext()){
            sum = sum + valuesIt.next().get();
        }

        context.write(key, new IntWritable(sum));
    }
}
