package cn.ls.top

/**
  * Created by Administrator on 2016/6/22.
  */

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object ImfTopN {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("TopNGroup").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val data = Array("Spark 100",
      "Hadoop 66",
      "Apache 99",
      "Hadoop 61",
      "Spark 95",
      "Hadoop 61",
      "Spark 98",
      "Hadoop 64",
      "Spark 68",
      "Hadoop 60",
      "Apache 60",
      "Apache 95",
      "Hadoop 67",
      "Spark 68",
      "Hadoop 69",
      "Spark 95",
      "Hadoop 64",
      "Spark 68",
      "Hadoop 64",
      "Spark 69");

    val lines = sc.parallelize(data)
    val pairs = lines.map(line => (line.split(" ")(0), line.split(" ")(1))) //生成key-v键值对一方便sortByKey排序
    val groupPairs = pairs.groupByKey()
    val sortedData = groupPairs.map(groupedData => {
      val groupedKey = groupedData._1 //获取分组的组名
      val groupValue = groupedData._2 //获取每组的内容集合
      import scala.collection.mutable.ListBuffer
      val listBuffer = new ListBuffer[Int]
      for (i <- groupValue) {
        listBuffer += i.toInt
      }
      val top5 = listBuffer.sorted(Ordering.Int.reverse).take(5)
      (groupedKey, top5)
    }).sortByKey(true)
    sortedData.foreach(println)

    // 方式二
    /*val pairs=lines.map { line => (line.split(" ")(0),line.split(" ")(1).toInt) }
    val grouped=pairs.groupByKey
    val groupedTop5=grouped.map(grouped=>
    {
      (grouped._1,grouped._2.toList.sortWith(_<_).take(5))
    })
    val groupedKeySorted=groupedTop5.sortByKey()
    groupedKeySorted.collect().foreach(pair=>{
      println(pair._1+":")
      pair._2.foreach { println }
    })*/

    sc.stop()
  }
}
