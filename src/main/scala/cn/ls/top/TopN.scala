package cn.ls.top

import org.apache.spark.util.random


/**
  * Created by Administrator on 2016/6/22.
  */
object TopN {

  def str(value: Any): Any = ???
  // sql方式
//  var data =["product" + str(random.XORShiftRandom(1, 10)) + " url" +
//    str(random.randint(1, 100)) for index in xrange((1000))]

  // 非sql方式
  /*data.map(line =>"""\d+\.\d+\.\d+\.\d+""".r.findAllIn(line).mkString).filter(_ != "").
    map(word => (word, 1)).reduceByKey(_ + _).map(word => (word._2, word._1)).sortByKey(false).
    map(word => (word._2, word._1)) take 50*/


}
