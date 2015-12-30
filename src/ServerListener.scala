import java.io._
import java.net.Socket

/**
  * Created by Caleb Prior on 30-Dec-15.
  */
class ServerListener(socket: Socket, server: ServerTrait) extends Runnable {
  var bufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream))
  var bufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream)))
  val ipAddress = socket.getLocalAddress.toString.drop(1)
  val port = socket.getLocalPort
  var studentId = "b486d209d797bffeeb7e1fd3b62923902e4922ddce8eb4cc4646017d1680a52c"

  def run(): Unit = {
    println("WORKER: " + Thread.currentThread.getId + " started")

    try {
      while (!socket.isClosed) {
        if (socket.getInputStream.available() > 0) {
          var message = ""
          message = bufferIn.readLine()
          println("WORKER: " + Thread.currentThread.getId + " received message: " + message)

          handleMessage(message)
        }
      }
    } catch {
      case e:Exception =>
        println("WORKER: " + Thread.currentThread.getId + " EXCEPTION " + e.getMessage)
    }
  }

  def handleMessage(message: String): Unit = {
    if (isKillService(message)) {
      killService()
    } else {
      println("WORKER: " + Thread.currentThread.getId + " unknown message")
    }
  }

  def isKillService(message: String): Boolean = {
    message.equals("KILL_SERVICE")
  }

  def killService(): Unit = {
    server.shutdown()
    socket.close()
  }
}
