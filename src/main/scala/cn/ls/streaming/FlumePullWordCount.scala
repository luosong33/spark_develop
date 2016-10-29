package cn.ls.streaming

import java.net.InetSocketAddress

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object FlumePullWordCount {
  // 主动从flume拉取数据
  def main(args: Array[String]) {

    LoggerLevels.setStreamingLogLevels()

    val conf = new SparkConf().setAppName("FlumePullWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))
    //从flume中拉取数据(flume的地址)
    val address = Seq(new InetSocketAddress("192.168.92.132", 8888)) // , new InetSocketAddress("192.168.92.132", 8888)可以有多个拉取地址)
    val flumeStream = FlumeUtils.createPollingStream(ssc, address, StorageLevel.MEMORY_AND_DISK) // StorageLevel.MEMORY_AND_DISK存储级别
    val words = flumeStream.flatMap(x => new String(x.event.getBody().array()).split(" ")).map((_,1))
    val results = words.reduceByKey(_+_)
    results.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
