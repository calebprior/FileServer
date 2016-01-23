import java.util.Base64

/**
  * Created by Caleb Prior on 22-Jan-16.
  */
class DirectoryMessageHandler(socketHandler: SocketHandler) extends MessageHandler(socketHandler){
  def handleMessage(msg: String): Unit = {
    MessageType.getMessageType(msg) match {
      case MessageType.LookUp => handleLookup()
      case MessageType.WriteFile => handleWriteFile()
      case MessageType.None => println("WORKER: " + Thread.currentThread.getId + " unknown message")
    }
  }

  def handleLookup():Unit = {

  }

  def handleWriteFile():Unit = {
    var fileName = socketHandler.readLine().split(':')(1).trim
    var length = Integer.parseInt(socketHandler.readLine().split(':')(1).trim())

    var bytesIn = socketHandler.readBytes(length)

    var bytes = Base64.getDecoder.decode(bytesIn.toString("UTF-8"))

    FileIOHelper.writeFile("testServer", bytes)
  }
}

class DirectoryEntry(filePath: String, fileId: Int, fileServerId: Int)
