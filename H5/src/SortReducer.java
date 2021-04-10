import java.io.*;
import java.util.*;
import java.lang.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;

//public class SortReducer extends Reducer<MyWritableComparable, IntWritable, 
//       MyWritableComparable, IntWritable> {
public class SortReducer extends Reducer<MyWritableComparable, NullWritable, 
       MyWritableComparable, NullWritable> {
    //public void reduce(MyWritableComparable key, Iterable<IntWritable> values, Context context)
    public void reduce(MyWritableComparable key, Iterable<NullWritable> values, Context context)
        throws IOException, InterruptedException {
        //IntWritable result = new IntWritable(1);

        //context.write(key, result);
        context.write(key, NullWritable.get());
        
    }
}

