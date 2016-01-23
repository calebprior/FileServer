import java.net.{InetAddress, Socket}

/**
 * Created by Caleb Prior on 12-Oct-15.
 */
object FrontEndClient {
  def main(args: Array[String]) {
//    val s = new Socket(InetAddress.getByName("178.62.121.108"), 443)

    val message = io.StdIn.readLine("Enter FileName> ")

    //var byteArray:Array[Byte] = FileIOHelper.readFile(message)

    val conn = new SocketHandler(new Socket(InetAddress.getByName("localhost"), 400))

    //println(byteArray.toString)
    //var asStr = Base64.getEncoder.encode(byteArray)

    //conn.sendLines(new WriteFile("test.txt", asStr.length).asString)
    //conn.sendBytes(asStr)

    conn.sendLines(new Messages.LookUp(message, true).toString)

    while(conn.isConnected){
      if(conn.hasContent){
        println(conn.readLine())
      }
    }
  }
}