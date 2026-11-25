import org.apache.spark.sql.SparkSession

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("E-Commerce Sales Analysis")
      .getOrCreate()

    try {
      spark.range(1, 4).show()
    } finally {
      spark.stop()
    }
  }
}