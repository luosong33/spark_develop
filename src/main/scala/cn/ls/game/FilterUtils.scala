package cn.ls.game

import java.text.SimpleDateFormat

import org.apache.commons.lang3.time.FastDateFormat

/**
  * Created by Administrator on 2016/5/25.
  */
object FilterUtils {

  // 次日留存
  def filterByTypeAndTime(fileds: Array[String], REGISTER: String, beginTime: Long,endTime: Long): Boolean = {
    val _type = fileds(0)
    val _time = fileds(1)
    val logTime = dateFormat.parse(_time).getTime
    REGISTER == _type && logTime >= beginTime && logTime < endTime
  }

  // 日活跃用户DAU
  /*def filterByTypes(fileds: Array[String], REGISTER: String, LOGIN: String): Boolean = {
    fileds(0) == REGISTER || fileds(0) == LOGIN
  }*/
  def filterByTypes(fileds: Array[String], REGISTER: String*): Boolean = { // 多个可变参数String*
    for (et <- REGISTER) {
      if(fileds(0) == et)
        return true
    }
    false
  }

  //val sdf = new SimpleDateFormat("") // 线程安全问题，可能多个线程调用它
  // 方案一：用线程共享
  val dateFormat = FastDateFormat.getInstance("yyyy年MM月dd日,E,HH:mm:ss")

  // 判断日期在指定范围内
  def filterByTime(fields: Array[String], startTime: Long, endTime: Long): Boolean = {
    val time = fields(1)
    val logTime = dateFormat.parse(time).getTime
    logTime >= startTime && logTime < endTime
  }

  // 日新增用户DNU
  def filterByType(fileds: Array[String], REGISTER: String): Boolean = {
    val _type = fileds(0)
    REGISTER == _type
  }


}
