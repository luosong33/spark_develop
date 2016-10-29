package cn.ls.hellospark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2016/5/23.
  */
object WordCount {
  def main(args: Array[String]) {


    //  通向spark的入口
    val conf = new SparkConf().setAppName("WC")
    val sc = new SparkContext(conf)
    // sortBy(_._2,false)   _表示元组，._2表示元组内的第二个参数
    sc.textFile(args(0)).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).sortBy(_._2,false).saveAsTextFile(args(1))

    sc.stop()


  }
}
