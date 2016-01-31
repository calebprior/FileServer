/**
  * Created by Caleb Prior on 30-Dec-15.
  */
class ServerListener(socketHandler: SocketHandler, server: ServerTrait, messageHandler: MessageHandler) extends Runnable {
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
        println("WORKER: " + Thread.currentThread.getId + " EXCEPTION " + e.getMessage)
        e.printStackTrace()
    }
  }

  def handleMessage(message: String): Unit = {
    if (isKillService(message)) {
      killService()
    } else {
      messageHandler.handleMessage(message)
    }
  }

  def isKillService(message: String): Boolean = {
    message.equals("KILL_SERVICE")
  }

  def killService(): Unit = {
    server.shutdown()
    socketHandler.close()
  }
}
