package cn.ls.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by ZX on 2015/6/22.
  */
object FlumePushWordCount {

  def main(args: Array[String]) { // 192.168.92.1 8888
    LoggerLevels.setStreamingLogLevels()

    val host = args(0) // 由flume推送，ip为spark所在机器ip(ping不同需断网用vm8ip)
    val port = args(1).toInt
    //val conf = new SparkConf().setAppName("FlumeWordCount")//.setMaster("local[2]")
    val conf = new SparkConf().setAppName("FlumeWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))
    //推送方式: flume向spark发送数据
    val flumeStream = FlumeUtils.createStream(ssc, host, port)
    //flume中的数据通过event.getBody()才能拿到真正的内容
    val words = flumeStream.flatMap(x => new String(x.event.getBody().array()).split(" ")).map((_, 1))

    val results = words.reduceByKey(_ + _)
    results.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
