import java.io.*;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;

public class MyWritableComparable implements 
    WritableComparable <MyWritableComparable>{
  // Some data
  private String key;
  private String word;
  private int count;

  // Default constructor to allow (de)serialization
  MyWritableComparable() { }

  public void write(DataOutput out) throws IOException {
    Text text = new Text();
    Text key_text = new Text();
    IntWritable intW = new IntWritable();
    key_text.set(key);
    key_text.write(out);
    text.set(word);
    text.write(out);
    intW.set(count);
    intW.write(out);
  }

  public void readFields(DataInput in) throws IOException {
    Text textW = new Text();
    Text key_text = new Text();
    IntWritable intW = new IntWritable();
    key_text.readFields(in);
    textW.readFields(in);
    intW.readFields(in);
    key = key_text.toString();
    word = textW.toString();
    count = intW.get();
  }

  public static MyWritableComparable read(DataInput in) throws IOException {
    MyWritableComparable w = new MyWritableComparable();
    w.readFields(in);
    return w;
  }

  public void setResult(String first, String new_word, int new_count) {
    word = new_word;
    key = first;
    count = new_count;
  }

  public String getString() {
    return word;
  }


  public int compareTo(MyWritableComparable o) {
    int thisCount = this.count;
    int thatCount = o.count;
    return (thisCount < thatCount ? -1 : 1);
  }

  public int hashCode() {
    return key.hashCode();
  }


  @Override
  public String toString() {
    return key + " " + word + " " + count;
  }
}
