package src

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.mllib.rdd.RDDFunctions._
import SparkContext._

import scala.collection.mutable.ListBuffer

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.analysis._
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.ru.RussianAnalyzer

import org.apache.lucene.util.Version

object Main {

  def main(args: Array[String]) {
    val appName = "SparkWordCount"
    val jars = List(SparkContext.jarOfObject(this).get)
    println(jars)
    val conf = new SparkConf().setAppName(appName).setJars(jars)
    val sc = new SparkContext(conf)
    if (args.length < 2) {
      println(args.mkString(","))
      println("ERROR. Please, specify input and output directories.")
    } else {
      val inputDir = args(0)
      val outputDir = args(1)
      println("Input directory: " + inputDir)
      println("Output directory: " + outputDir)
      run(sc, inputDir, outputDir)
    }
  }

  def splitLucene( line:String ) : List[String] = {
    var res = new ListBuffer[String]()
    val analyzer = new RussianAnalyzer()
    val stream = analyzer.tokenStream("field", line)

    val termAtt = stream.addAttribute(classOf[CharTermAttribute])
    stream.reset()
    while (stream.incrementToken()) {
        res += termAtt.toString()
    }

    return res.toList
  }

  def run(sc: SparkContext, inputDir: String, outputDir: String) {
    val file = sc.textFile(inputDir + "/war-and-peace-[1-4].txt")
    val lines = file.map(line => line.replaceAll("""[-]""", "").toLowerCase())
    val res = file.flatMap(splitLucene(_)).
      sliding(2).
      map{ case Array(x, y) => ((x, y), 1) }.
      reduceByKey( _ + _ ).
      //sortBy( z => (z._2, z._1._1, z._1._2), ascending = false )
    //res.saveAsTextFile(outputDir)
      sortBy( z => (z._2, z._1._1, z._1._2), ascending = false ).
      first()
    println(res)
  
  }

}
