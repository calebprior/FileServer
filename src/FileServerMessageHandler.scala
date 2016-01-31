import java.util.Base64

/**
  * Created by Caleb Prior on 23-Jan-16.
  */
class FileServerMessageHandler(socketHandler: SocketHandler, primaryNode:Boolean) extends MessageHandler(socketHandler) {
  var fileManager = new FileManager()

  def handleMessage(msg: String): Unit = {
    MessageTypes.getMessageType(msg) match {
      case MessageTypes.WriteFile => handleWriteFile()
      case MessageTypes.ReadFile => handleReadFile()
    }
  }

  def handleWriteFile(): Unit ={
    // Use primary copy model! writes to a primary propagates the writes to replicas
    // Can read from any replica or primary node

    val fileName = socketHandler.readLine().split(':')(1).trim
    val length = Integer.parseInt(socketHandler.readLine().split(':')(1).trim())
    val fileBytes = readInFileBytes(length)

    if(!fileManager.fileExists(fileName)){
      fileManager.addNewFile(fileName)
    }
    FileIOHelper.writeFile("0", fileBytes)

    if(primaryNode){

    } else {

    }
  }

  def readInFileBytes(length:Int): Array[Byte] = {
    val bytesIn = socketHandler.readBytes(length)
    Base64.getDecoder.decode(bytesIn.toString("UTF-8"))
  }

  def handleReadFile(): Unit ={

  }
}
