package cn.ls.game

import java.util

import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

/**
  * Created by Administrator on 2016/7/1.
  */
object JedisConnectionPool {

  val config = new JedisPoolConfig()
  // 最大连接数
  config.setMaxTotal(10)
  // 最大空闲数
  config.setMaxIdle(5)
  // 当调用borrow Object方法时，是否进行有效性校验
  config.setTestOnBorrow(true)

  val pool = new JedisPool(config, "192.168.92.109", 6379)

  def getConnection(): Jedis = {
    pool.getResource
  }

  def main(args: Array[String]) {
    val connection = JedisConnectionPool.getConnection()
    val keys: util.Set[String] = connection.keys("*") // connection.set("test", "luosong")
    println(keys)

    connection.close()
  }

}
