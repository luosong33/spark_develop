package cn.ls.game

import java.text.SimpleDateFormat
import java.util.Calendar

/**
  * Created by root on 2016/5/23.
  */
object TimeUtils {

  val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  val calendar = Calendar.getInstance()

  def apply(time: String) = {
    calendar.setTime(simpleDateFormat.parse(time)) // 存时间
    calendar.getTimeInMillis // 转换成long
  }

  def getCertainDayTime(amount: Int): Long ={
    calendar.add(Calendar.DATE, amount)
    val time = calendar.getTimeInMillis
    calendar.add(Calendar.DATE, -amount)
    time
  }



}
