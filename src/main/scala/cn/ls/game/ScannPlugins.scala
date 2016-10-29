package cn.ls.game

import java.text.SimpleDateFormat

import cn.ls.streaming.LoggerLevels
import kafka.serializer.StringDecoder
import org.apache.commons.lang3.time.FastDateFormat
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Milliseconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

/**
  * Created by Administrator on 2016/7/1.
  */
object ScannPlugins {

  def main(args: Array[String]) {

    LoggerLevels.setStreamingLogLevels() // 设置日志级别
    val dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss") // 线程安全的。// val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") // 是在diver里用，所以没有线程问题，非直连方式可以用这个

    val Array(zkQuorum, group, topics, numThreads) = Array("autoMaster:2181,auto1:2181,auto2:2181", "g0", "gamelogs", "1") // autoMaster:2181,auto1:2181,auto2:2181 g1 test 2  如果没接收到，删掉1、2
    val conf = new SparkConf().setAppName("ScannPlugins").setMaster("local[2]") // conf.set("auto.offset.reset","smallest") // 已经有kafkaparams了，不需要了。smallest表示启动之前产生的数据也消费，重头读
    conf.set("spark.serializer","org.apache.spark.serializer.KryoSerializer")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Milliseconds(10000))
    ssc.checkpoint("c://ck3") // 如果是集群，就写hdfs地址

    val kafkaParams = Map[String, String](
      "zookeeper.connect" -> zkQuorum,
      "group.id" -> group, // 相当于实时、离线部门，各自从kafka消费全部数据
      "auto.offset.reset" -> "smallest"
//      "auto.offset.reset" -> "latest"
    )

    // logstash拉取数据到kafka，从kafka消费数据
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap // 切分topic，receiver启numThreads.toInt个线程一起读消费，tomap转换成map格式；topicMap表示可以放多个topic
    val dstream = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicMap, StorageLevel.MEMORY_AND_DISK_SER) /*采用createStream的方式创建DStream，它会与kafka点对点建立连接，并在spark的work的exscutor进程中创建启动receiver对象，不停去kafka的topic中拉取数据（时间间隔在启动kafka时指定），它相当与kafka的消费者，但是如果5秒内，信息过大，内存溢出，会数据丢失。StorageLevel.MEMORY_AND_DISK_SER存储级别*/
    val lines = dstream.map(_._2) // kafka可以存key、value，这里只有value，拿出来
    val filteredLines = lines.map(_.split("\t")).filter(f => f.size > 8).filter( f => {
        f(3) == "11" && f(8) == "强效太阳水"
//        f(3) == "4" && f(8) == "恶魔铃铛"
      }) // 过滤事件类型
    filteredLines.print()

    // 分组聚合
    val groudWindow: DStream[(String, Iterable[Long])] = filteredLines.map(f => (f(7), dateFormat.parse(f(12)).getTime)).groupByKeyAndWindow(Milliseconds(30000), Milliseconds(20000)) // 窗口长度30秒，滑动长度20秒。按key进行分组聚合
    val filtered: DStream[(String, Iterable[Long])] = groudWindow.filter(_._2.size >= 5) // 过滤掉使用少于5次的用户

    val itemAvgTime = filtered.mapValues(it => {
      val list = it.toList.sorted // sorted排序
      val size = list.size
      val first = list(0)
      val last = list(size - 1)
      val cha : Double = last - first
      cha / size
    })

    val badUser: DStream[(String, Double)] = itemAvgTime.filter(_._2 < 1000) // 开外挂的用户
    badUser.print()

    badUser.foreachRDD(rdd => { // 循环所有RDD
      rdd.foreachPartition(it => { // 循环RDD的所有分区的所有数据，操作数据集
        val connection = JedisConnectionPool.getConnection()
        it.foreach(t => { // 循环每条数据，操作数据
          val user = t._1
          val avgTime = t._2
          val currentTime = System.currentTimeMillis() // 现在的时间
          connection.set(user+"_"+currentTime,avgTime.toString)
          println("=========="+t._1+":"+t._2+"==========")
        })
        connection.close()
      })
    })


    ssc.start()
    ssc.awaitTermination()


  }


}
