import org.apache.spark.sql.SparkSession

case class Person(name: String, age: Int, boo: Boolean, time: Long)

case class Address(name: String, city: String)

object AnalyzerLogs {
  def main(args: Array[String]) {
    //    // create sparkConf
    //    val sparkConf = new SparkConf()
    //      .setAppName("LogAnalyzer")
    //      .setMaster("local[2]")
    // create sparksession
    val sparkSession = SparkSession
      .builder()
      .appName("LogAnalyzer")
      .config("spark.some.config.option", "some-value")
      .master("local[2]")
      .getOrCreate()
    // For implicit conversions like converting RDDs to DataFrames
    //    // create sparkContext
    //    val sc = new SparkContext(sparkConf)
    //    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    //    //create DataFrame
    //    import sqlContext.implicits._
    //    val people = sc.textFile("D:\\people.txt")
    //      .map(_.split(",")).map(p => Person(p(0),p(1).trim.toInt)).toDF()
    // $example on:create_df$
    //    val people=Seq(("Deng",20), ("Li",30), ("Fang",15)).map(p => Person(p._1, p._2))
    val people = sparkSession.createDataFrame(Seq(("Deng", 20, true, 1497888805948L), ("Li", 30, true, 1497889684406L), ("Fang", 15, false, 1497889995051L)).map(p => Person(p._1, p._2, p._3, p._4)))
    val address = sparkSession.createDataFrame(Seq(("Deng", "Shanghai"), ("Li", "Shanghai"), ("Fang", "Hangzhou")).map(a => Address(a._1, a._2)))
    people.createOrReplaceTempView("people")
    address.createOrReplaceTempView("address")
    // SQL statements can be run by using the sql methods provided by sqlContext.
    //    val teenagers = sqlContext.sql("SELECT age,count(age) c FROM people WHERE age >= 13 AND age <= 19 group by age order by age")

    //    val teenagers = sqlContext.sql("select name,age from people where age > 18")
    //    println(teenagers.queryExecution.toString())

    //    val subquery01 = sqlContext.sql("select name,age from people where name in ( select name from address where city='Shanghai')")
    //    println(subquery01.queryExecution.toString())

    //    val subquery02 = sqlContext.sql("select age,(select count(city) from address a where a.name = p.name) from people p")
    //    println(subquery02.queryExecution.toString())
    import sparkSession.implicits._
    //    val subquery03 = sparkSession.sql("select name,age from people where age = (select max(age) from people where boo = true ) and name = 'Li'")
    //    val subquery03 = sparkSession.sql("select name from people where boo = true order by name limit 10")
    val subquery03 = sparkSession.sql("select p.name,p.age,a.city from people as p inner join address as a on p.name = a.name where p.age = 20 and a.city = 'Shanghai' order by p.name limit 1")
    //val subquery03 = sparkSession.sql("select name,count(name) from people group by name having count(name)>1")
    //val subquery03 = sparkSession.sql("select name from (select name from people order by age ) tab offset 1 limit 2")
    //val subquery03 = sparkSession.sql("select count(name),sum(age) from people p1 where exists (select * from people p2 where p1.name =p2.name and p1.boo <> p2.boo) limit 2 ")
    //  val subquery03 = sparkSession.sql("select * from people where age between (select distinct age + 1 from people where age = 20) and (select distinct age + 1 from people where age = 15)")
    //val subquery03 = sparkSession.sql("select * from people where age like '2%'")
    //    val subquery03 = sparkSession.sql("select a.city from people p,address a where p.age in (select age from people where name = 'Deng') and p.name = a.name ")
    //val subquery03 = sparkSession.sql("select * from people where age in (select sum(age) from people)")
    println(subquery03.queryExecution.toString())
    println()

    //limit
    println("maxRows:" + subquery03.queryExecution.optimizedPlan.maxRows)
    println(subquery03.queryExecution.optimizedPlan.nodeName)



    //一些字段的属性，比如字段名、编号、类型
    println(subquery03.queryExecution.executedPlan.allAttributes.attrs)
    println(subquery03.queryExecution.executedPlan.allAttributes.toStructType)

    println()
    //就是字段
    println(subquery03.queryExecution.executedPlan.output)


    println(subquery03.queryExecution.executedPlan.metadata)

    println(subquery03.queryExecution.executedPlan.outputOrdering)

    println(subquery03.queryExecution.executedPlan.expressions)

    //执行计划的第一行
    println(subquery03.queryExecution.executedPlan.simpleString)

    //整个执行计划，并且以树的形式打印
    println("execute tree string:" + subquery03.queryExecution.executedPlan.treeString)
    //执行计划节点名字
    println("node name is " + subquery03.queryExecution.executedPlan.nodeName)

    //没有理解
    println("execute child 0:" + subquery03.queryExecution.executedPlan.children(0).nodeName)

    subquery03.printSchema()

    println(subquery03.queryExecution.sparkPlan.collectLeaves().apply(0).nodeName)

    //val rdd= subquery03.mapPartitions(
    //  rows => {
    //    while(rows.hasNext){
    //      val row = rows.next()
    //      println("++++++++++++++++++" + row(0))
    //    }
    //    Iterator(1)
    //  })
    //    val t =rdd.count()
    subquery03.show()
    //      println(subquery03.count())
    // The results of SQL queries are DataFrames and support all the normal RDD operations.
    // The columns of a row in the result can be accessed by field index:
    //    teenagers.map(t => "age" + t(1)).collect().foreach(println)
    // or by field name:
    //teenagers.map(t => "Name: " + t.getAs[String]("name")).collect().foreach(println)
    //    teenagers.select("age","c").collect().foreach(println)
    // row.getValuesMap[T] retrieves multiple columns at once into a Map[String, T]
    //  teenagers.map(_.getValuesMap[Any](List("name", "age"))).collect().foreach(println)
    // Map("name" -> "Justin", "age" -> 19)
  }
}
