import java.net.{InetAddress, Socket}
import java.nio.file.{Paths, Files}
import java.util.Base64

import scala.io.BufferedSource

/**
 * Created by Caleb Prior on 12-Oct-15.
 */
object EchoClient {
  def main(args: Array[String]) {
//    val s = new Socket(InetAddress.getByName("178.62.121.108"), 443)

    val message = io.StdIn.readLine("Enter FileName> ")

    var byteArray:Array[Byte] = FileIOHelper.readFile(message)

    val conn = new SocketHandler(new Socket(InetAddress.getByName("localhost"), 400))

    println(byteArray.toString)
    var asStr = Base64.getEncoder.encode(byteArray)

    conn.sendLines("WRITE_FILE\n" + "filename:"+"test.txt\n" + "length:"+ asStr.length)
    conn.sendBytes(asStr)
  }
}