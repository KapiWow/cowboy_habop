import java.io.*;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;

public class MyWritable implements Writable {
  // Some data
  private String word;
  private int count;

  // Default constructor to allow (de)serialization
  MyWritable() { }

  public void write(DataOutput out) throws IOException {
    Text a = new Text();
    IntWritable intW = new IntWritable();
    a.set(word);
    intW.set(count);
    a.write(out);
    intW.write(out);
  }

  public void readFields(DataInput in) throws IOException {
    Text textW = new Text();
    IntWritable intW = new IntWritable();
    textW.readFields(in);
    intW.readFields(in);
    word = textW.toString();
    count = intW.get();
  }

  public static MyWritable read(DataInput in) throws IOException {
    MyWritable w = new MyWritable();
    w.readFields(in);
    return w;
  }

  public void setResult(String new_word, int new_count) {
    word = new_word;
    count = new_count;
  }

  public String getString() {
    return word;
  }


  @Override
  public String toString() {
    return word + " " + count;
  }
}
