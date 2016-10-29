package cn.ls.game


import cn.ls.streaming.LoggerLevels
import org.apache.spark.{SparkConf, SparkContext}


/**
  * Created by Administrator on 2016/5/24.
  */
object GameKPI {

  def main(args: Array[String]) {

    LoggerLevels.setStreamingLogLevels() // 设置日志级别

    val queryTime = "2016-02-02 00:00:00"
    val beginTime = TimeUtils(queryTime)
    val endTime = TimeUtils.getCertainDayTime(+1)

    val conf = new SparkConf().setAppName("GameKPI").setMaster("local[*]") // *表示有几个核，也可以写数字模拟多个线程
    val sc = new SparkContext(conf)

    // 切分后数据
    //    val splitedLogs = sc.textFile(args(0)).map(_.split("\\|"))
    val splitedLogs = sc.textFile("F:\\IDER\\insert\\IdeaProjects\\spark-develop\\src\\main\\resources\\game\\GameLog.txt").map(_.split("\\|"))
    // 根据时间过滤后缓存，可重用
    val filterLogs = splitedLogs.filter(fileds => FilterUtils.filterByTime(fileds, beginTime, endTime)).cache()

    // 日新增：DNU(Daily New Users)
    val dnu = filterLogs.filter(fileds => FilterUtils.filterByType(fileds, EventType.REGISTER))
      .count()
    println("=============日新增dnu:" + dnu + "==============")

    // 日活跃用户 DAU(Daily Active Users)
    val dau = filterLogs.filter(fileds => FilterUtils.filterByTypes(fileds, EventType.REGISTER, EventType.LOGIN))
      .map(_ (3)) // 拿到角色名
      .distinct() // 筛选出角色名不同的
      .count()
    println("=============日活跃dau:" + dau + "==============")

    // 次日留存(Day 1 Retention Ratio)
    val drrTime = TimeUtils.getCertainDayTime(-1)
    val lastDay = splitedLogs.filter(fileds => FilterUtils.filterByTypeAndTime(fileds, EventType.REGISTER, drrTime, beginTime))
      .map(x => (x(3), 1)) // 前一日新增用户
    val toDayLoginUser = filterLogs.filter(fileds => FilterUtils.filterByTypes(fileds, EventType.REGISTER, EventType.LOGIN))
      .map(x => (x(3), 1)) // 今日活跃
      .distinct()
    // 次日留存数
    val d1r: Double = lastDay.join(toDayLoginUser).count()
    println("=============日活跃d1r:" + d1r + "==============")
    // 次日留存率
    val d1rr = d1r / lastDay.count()
    println("=============日活跃d1rr:" + d1rr + "==============")


    sc.stop()
  }

}
