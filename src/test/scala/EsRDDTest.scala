/**
  * Created by Administrator on 2016/7/3.
  */

import cn.ls.streaming.LoggerLevels
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark._

object EsRDDTest {

  def main(args: Array[String]) {

    LoggerLevels.setStreamingLogLevels() // 设置日志级别
    val conf = new SparkConf().setAppName("EsRDDTest").setMaster("local")
    conf.set("es.nodes", "autoMaster,auto1,auto2")
    conf.set("es.port", "9200")
    conf.set("es.index.auto.create", "true")
    val sc: SparkContext = new SparkContext(conf)

    val item = "4"
    val resource = "gamelogs"
    val query: String =
      s"""{
        "query":{"match_all":{}},
          "filter":{
            "bool":{
              "must":[
              {"term":{"event_type":$item}}
            ]
          }
        }
      }"""
    val eslogs = sc.esRDD(resource, query) // ? 只有event_type、current_X、current_y、item_id  可以查询
    println("===================="+eslogs.collect()+"====================")
    println("===================="+eslogs.collect().size+"====================")

  }

}