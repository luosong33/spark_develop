package cn.ls.streaming

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2016/6/11.
  * (从集群  nc -lk 8888 拉取数据)
  */
object StreamingWrodCount {
  def main(args: Array[String]) {

    LoggerLevels.setStreamingLogLevels() // 日志级别

    // streamingContext
    val conf = new SparkConf().setAppName("StreamingWrodCount").setMaster("local[2]") // 2:多个线程
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(5))

    // Dstream 特殊RDD
    val ds = ssc.socketTextStream("192.168.92.132", 8888)
    val result = ds.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
    result.print()

    ssc.start() // 启动任务
    ssc.awaitTermination() // 等待结束。可以外部给他下发指令结束

  }

}
