package com.cds.bigdata.leran

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.UDETableInputFormat
import org.apache.hadoop.hbase.protobuf.ProtobufUtil
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos
import org.apache.hadoop.hbase.util.{Base64, Bytes}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.hadoop.hbase.client.Scan

class HBaseScanTest {

  def createHBaseRDD(sc: SparkContext,
                     zk: String,
                     tableName: String,
                     scan: String,
                     requiredColumn: String):
  RDD[String] = {

    val conf = HBaseConfiguration.create()
    conf.set(UDETableInputFormat.INPUT_TABLE, tableName)
    conf.set("hbase.zookeeper.quorum", zk)
    conf.set("hbase.zookeeper.property.clientPort", "2181")
    conf.set(UDETableInputFormat.SCAN, scan)
    conf.set(UDETableInputFormat.SCAN_CACHEDROWS, "100")
    // for zk retry
    conf.setInt("hbase.client.retries.number", 3)
    conf.setInt("zookeeper.recovery.retry", 3)
    //for hbase scanner
    conf.set("hbase.client.scanner.timeout.period", "600000")
    conf.set("hbase.rpc.timeout", "120000")

    val resultsRDD = sc.newAPIHadoopRDD(conf, classOf[UDETableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

    val kvRDD = resultsRDD.map((r: (ImmutableBytesWritable, org.apache.hadoop.hbase.client.Result)) => {
      Bytes.toString(r._2.getValue(Bytes.toBytes("cf"), Bytes.toBytes(requiredColumn)))
    })


    kvRDD
  }

  def scanData(sc: SparkContext) {

//    val scan = new Scan()


    val proto: ClientProtos.Scan = ProtobufUtil.toScan(new Scan())
    val scan = Base64.encodeBytes(proto.toByteArray)


    val scanRDD = createHBaseRDD(sc, "hbp213:2181", "testTable1", scan, "name")

    try {
      scanRDD.collect().foreach(println(_))
    } catch {
      case e: Exception =>
        println("jelly: " + e.getMessage + e.getStackTraceString)
    }

  }
}

object HBaseScanTest {

  def main(args: Array[String]) {

    val sc = new SparkConf()
    sc.setAppName("HBaseScanTest")
    sc.setMaster("local[1]")
    val contex = new SparkContext(sc)

    for (i <- 1 to 100) {
      new HBaseScanTest().scanData(contex)
    }

    Thread.sleep(1000000)
  }

}
