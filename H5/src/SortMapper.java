import java.io.*;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class SortMapper extends Mapper<LongWritable, Text, 
       MyWritableComparable, NullWritable> {

    private Text word = new Text();

    public void map(LongWritable key, Text value,
            Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        String[] words = line.split(" ");
        String[] words2 = words[0].split("	");
        MyWritableComparable res = new MyWritableComparable();
        if (words.length != 3) {
            res.setResult(
                    words2[0],
                    words2[1],
                    Integer.parseInt(words[1])
            ); 
        } else {
          res.setResult(
                  words[0],
                  words[1],
                  Integer.parseInt(words[2])
          ); 
        }

        context.write(res, NullWritable.get());

    }
}
