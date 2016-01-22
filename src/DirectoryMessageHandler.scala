import java.util.Base64

/**
  * Created by Caleb Prior on 22-Jan-16.
  */
class DirectoryMessageHandler(socketHandler: SocketHandler) extends MessageHandler(socketHandler){
  def handleMessage(msg: String): Unit = {
    if(isWriteFile(msg)){
      handleWriteFile(msg)
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
}
