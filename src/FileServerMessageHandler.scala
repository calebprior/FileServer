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

    fileManager.writeFile(fileName, fileBytes)

    if(primaryNode){
      sendFileToReplicas(fileName, fileBytes)
    }

    socketHandler.sendLines(new Messages.WriteFileResponse(fileName).toString)
  }

  def readInFileBytes(length:Int): Array[Byte] = {
    val bytesIn = socketHandler.readBytes(length)
    Base64.getDecoder.decode(bytesIn.toString("UTF-8"))
  }

  def sendBytes(fileBytes:Array[Byte]): Unit = {
    val encoded = Base64.getEncoder.encode(fileBytes)
    socketHandler.sendBytes(encoded)
  }

  def sendFileToReplicas(fileName: String, fileBytes: Array[Byte]) = {

  }

  def handleReadFile(): Unit = {
    val fileName = socketHandler.readLine().split(':')(1).trim

    if(fileManager.fileExists(fileName)){
      val fileBytes = fileManager.getFileBytes(fileName)
      socketHandler.sendLines(new Messages.ReadFileResponse(fileName, fileBytes.length).toString)
      sendBytes(fileBytes)
    } else {
      socketHandler.sendLines(new Messages.ErrorFileNotFound(fileName).toString)
    }
  }
}
