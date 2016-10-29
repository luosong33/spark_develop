package cn.ls.game

import cn.ls.streaming.LoggerLevels
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark._

/**
  * Created by Administrator on 2016/7/3.
  */
object ElasticSpark {

  def main(args: Array[String]) {

    LoggerLevels.setStreamingLogLevels()
    // 设置日志级别
    val conf: SparkConf = new SparkConf().setAppName("ElasticSpark").setMaster("local")
    conf.set("es.nodes", "autoMaster,auto1,auto2")
    conf.set("es.port", "9200")
    conf.set("es.index.auto.create", "true")
    val sc: SparkContext = new SparkContext(conf)

    val startTime = 1461368000
    val endTime = 1461368929
    val tp = "1"

    /*val query: String =
      s"""{
         "query":{"match_all":{}},
         "filter":{
          "bool":{
            "must":{
              "range":{
                "access.time":{
                  "gte":"$startTime",
                  "lte":"$endTime"
                }
              }
            }
          }
         }
      }"""*/
    val query: String =
      s"""{
        "query":{"match_all":{}},
        "filter":{
          "bool":{
            "must":[
              {"term":{"access.type":$tp}},
              {
                "range":{
                  "access.time":{
                    "gte":"$startTime",
                    "lte":"$endTime"
                  }
                }
              }
            ]
          }
        }
      }"""
    val rdd1 = sc.esRDD("accesslogs", query)
    println("====================" + rdd1.collect() + "====================")
    println("====================" + rdd1.collect().size + "====================")

  }

}
