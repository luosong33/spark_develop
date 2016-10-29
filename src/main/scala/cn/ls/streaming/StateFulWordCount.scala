package cn.ls.streaming

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
  * Created by Administrator on 2016/6/11.
  */
object StateFulWordCount {
  def main(args: Array[String]) {
    // 带累加的wordcount(从集群  nc -lk 8888 拉取数据)
    // String表示单词，Seq表示当前批次，Option表示之前批次（用option是因为如果为空可通过getOrElse设为0（getOrElse如果有内容拿出来，如果第一次就为0））
    val updateFunc = (iter: Iterator[(String, Seq[Int], Option[Int])]) => {
      iter.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(m => (x, m)) }
    }

    LoggerLevels.setStreamingLogLevels() // 设置日志级别

    // streamingContext
    val conf = new SparkConf().setAppName("StreamingWrodCount").setMaster("local[2]")
    // 2:多个线程
    val sc = new SparkContext(conf)
    // 为了容错，必须设置记录
    sc.setCheckpointDir("c://ck")
    val ssc = new StreamingContext(sc, Seconds(5))

    // Dstream 特殊RDD
    val ds = ssc.socketTextStream("192.168.92.132", 8888)
    val result = ds.flatMap(_.split(" ")).map((_, 1)).updateStateByKey(updateFunc, new HashPartitioner(sc.defaultParallelism), true) // sc.defaultParallelism指定一个分区数（并行度）（默认返回一个分区数）；true表示记住原来的partitioner（是否记住原来的partition）
    result.print()

    ssc.start() // 启动任务
    ssc.awaitTermination() // 等待结束。可以外部给他下发指令结束
  }
}
