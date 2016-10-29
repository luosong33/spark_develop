package cn.ls.sql

import cn.ls.streaming.LoggerLevels
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2016/7/3.
  */
object TopNSql {

  def main(args: Array[String]) {
    LoggerLevels.setStreamingLogLevels() // 设置日志级别
    val conf: SparkConf = new SparkConf().setAppName("TopNSql").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    //    val lineRDD = sc.textFile("hdfs://autoMaster:9000/url_topN.txt").map(_.split(" "))
    val lineRDD = sc.textFile("F:\\IDER\\insert\\IdeaProjects\\spark-develop\\src\\main\\resources\\url_topN.txt").map(_.split(" "))

    val UrlPRDD = lineRDD.map(x => UrlP(x(0), x(1), x(2))) // 将RDD和case class关联
    import sqlContext.implicits._
    // 导入隐式转换，如果不到人无法将RDD转换成DataFrame
    val urlPDF = UrlPRDD.toDF
    //    println(urlPDF.show)

    urlPDF.registerTempTable("t_url") // 注册表
    val resu = sqlContext.sql("select url,count(*) as access from t_url group by url order by access desc")
    resu.map(t => ("url: " + t(0)," number: " + t(1))).collect().foreach(println) // 循环打印
//    resu.map(t => "url: " + t(0) + " number: " + t(1)).saveAsTextFile("hdfs://autoMaster:9000/url1/url")
  }

}

case class UrlP(name: String, age: String, url: String)
