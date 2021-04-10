import java.io.*;
import java.util.*;
import java.lang.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;

public class Summer extends Reducer<Text, MyWritable, Text, MyWritable> {
    public void reduce(Text key, Iterable<MyWritable> values, Context context)
        throws IOException, InterruptedException {
        Text nextWord = new Text();
        Iterator<MyWritable> valuesIt = values.iterator();
        String res = new String();

        Map<String, Integer> hashMap = new HashMap<>();

        while(valuesIt.hasNext()){
            res = valuesIt.next().getString();
            if (hashMap.containsKey(res)) {
                hashMap.put(res, hashMap.get(res)+1);   
            } else {
                hashMap.put(res, 1);   
            }
        }

        int max = 0;

        Set<Map.Entry<String, Integer>> set = hashMap.entrySet();

        for (Map.Entry<String, Integer> me : set) {
            if (me.getValue() > max) {
                max = me.getValue();
                res = me.getKey();
            }
        }
        
        MyWritable result = new MyWritable();
        result.setResult(res, max);

        context.write(key, result);
        
    }
}

