import java.io.*;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;

public class MyWritable implements Writable {
  private String word;
  private int count;

  MyWritable() { }

  public void write(DataOutput out) throws IOException {
    Text text = new Text();
    IntWritable intW = new IntWritable();
    text.set(word);
    intW.set(count);
    text.write(out);
    intW.write(out);
  }

  public void readFields(DataInput in) throws IOException {
    Text text = new Text();
    IntWritable intW = new IntWritable();
    text.readFields(in);
    intW.readFields(in);
    word = text.toString();
    count = intW.get();
  }

  public static MyWritable read(DataInput in) throws IOException {
    MyWritable myW = new MyWritable();
    myW.readFields(in);
    return myW;
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
