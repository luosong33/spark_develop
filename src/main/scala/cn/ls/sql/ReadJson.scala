package cn.ls.sql

import cn.ls.streaming.LoggerLevels
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2016/7/10.
  */
object ReadJson {


  def main(args: Array[String]) {

    // 设置日志级别
    LoggerLevels.setStreamingLogLevels()

    val conf: SparkConf = new SparkConf().setAppName("TopNSql").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val df: DataFrame = sqlContext.read.json("F:\\IDER\\insert\\IdeaProjects\\spark-develop\\src\\main\\resources\\people.json")
//    val df: DataFrame = sqlContext.read.format("json").load("F:\\IDER\\insert\\IdeaProjects\\spark-develop\\src\\main\\resources\\people.json")
    df.show()
  }

}
