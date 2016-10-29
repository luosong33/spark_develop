package cn.ls.streaming

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Administrator on 2016/6/18.
  */
object KafkaWordCount {

  val updateFunc = (iter: Iterator[(String, Seq[Int], Option[Int])]) => {
    iter.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(m => (x, m)) }
  }

  def main(args: Array[String]) {

    LoggerLevels.setStreamingLogLevels()

    // autoMaster:2181,auto1:2181,auto2:2181 g1 test 2  如果没接收到，删掉1、2
    val Array(zkQuorum, group, topics, numThreads) = args
    val sparkConf = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    ssc.checkpoint("c://ck2") // 如果是集群，就写hdfs地址

    //"alog-2016-04-16,alog-2016-04-17,alog-2016-04-18"
    //"Array((alog-2016-04-16, 2), (alog-2016-04-17, 2), (alog-2016-04-18, 2))" alog-2016-04-16表示topic（当天数据），2表示并行度几个线程（由cpu核数确定）
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap // 切分topic，receiver启numThreads.toInt个线程一起读消费，tomap转换成map格式
    val data = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap, StorageLevel.MEMORY_AND_DISK_SER) // topicMap表示可以放多个topic
    /*采用createStream的方式创建DStream，它会与kafka点对点建立连接，
    并在spark的work的exscutor进程中创建启动receiver对象，不停去kafka的topic中拉取数据（时间间隔在启动kafka时指定），它相当与kafka的消费者
    但是如果5秒内，信息过大，内存溢出，会数据丢失
    StorageLevel.MEMORY_AND_DISK_SER存储级别*/

    println(data)
    val words = data.map(_._2).flatMap(_.split(" "))
    val wordCounts = words.map((_, 1)).updateStateByKey(updateFunc, new HashPartitioner(ssc.sparkContext.defaultParallelism), true) // sc.defaultParallelism指定一个分区数（并行度）（默认返回一个分区数）；true表示记住原来的partitioner（是否记住原来的partition）

    wordCounts.print() // 生产中并不是做简单打印，而是拿到所有分区，做数据更新和其他业务逻辑
    /*wordCounts.mapPartitions(it => {
      // 1、jedis连接，一个分区一个jedis连接，迭代一个分区拿一个jedis更新数据到redis里
      it.map()  // 2、拿到某个分区中的每行
    })*/

    ssc.start()
    ssc.awaitTermination()

  }

}
