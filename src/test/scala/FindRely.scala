import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 2016/7/8.
  */
object FindRely {

  val rel = Array(("A", "B"), ("B", "C"), ("C", "D"), ("D", "E"))
  val result = ArrayBuffer[String]()

  def findRely(tb: String, re: Array[(String, String)]): Unit = {
    val map: Array[String] = re.filter(_._2 == tb).map(_._1)
//    println("==================" + map(0) + "==================")
    if (map.size > 0) {
      result += map(0)
      println("=========if=========" + map(0) + "=========if=========")
      findRely(map(0), re)
    }
  }

  def main(args: Array[String]) {
    findRely("D", rel)
    println(result.size+"result================="+result)
  }
}
