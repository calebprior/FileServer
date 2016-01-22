import java.io._
import java.net.Socket
import java.nio.file.{Paths, Files}
import java.util.Base64

/**
  * Created by Caleb Prior on 30-Dec-15.
  */
class ServerListener(socketHandler: SocketHandler, server: ServerTrait) extends Runnable {
  val ipAddress = socketHandler.getIpAddress
  val port = socketHandler.getPort
  var studentId = "b486d209d797bffeeb7e1fd3b62923902e4922ddce8eb4cc4646017d1680a52c"

  def run(): Unit = {
    println("WORKER: " + Thread.currentThread.getId + " started")

    try {
      while (socketHandler.isOpen) {
        if (socketHandler.hasContent) {
          var message = ""
          message = socketHandler.readLine()
          println("WORKER: " + Thread.currentThread.getId + " received message: " + message)
          handleMessage(message)
        }
      }
    } catch {
      case e:Exception =>
        println("WORKER: " + Thread.currentThread.getId + " EXCEPTION " + e.getMessage + " " + e.getStackTrace.toString)
    }
  }

  def handleMessage(message: String): Unit = {
    if(isWriteFile(message)){
      handleWriteFile(message)
    } else if (isKillService(message)) {
      killService()
    } else {
      println("WORKER: " + Thread.currentThread.getId + " unknown message")
    }
  }

  def isWriteFile(message:String): Boolean = {
    message.startsWith("WRITE_FILE")
  }

  def handleWriteFile(firstLine:String):Unit = {
    var fileName = socketHandler.readLine().split(':')(1).trim
    var length = Integer.parseInt(socketHandler.readLine().split(':')(1).trim())

    var bytesIn = socketHandler.readBytes(length)

    var bytes = Base64.getDecoder.decode(bytesIn.toString("UTF-8"))

    FileIOHelper.writeFile("testServer", bytes)
  }

  def isKillService(message: String): Boolean = {
    message.equals("KILL_SERVICE")
  }

  def killService(): Unit = {
    server.shutdown()
    socketHandler.close()
  }
}
