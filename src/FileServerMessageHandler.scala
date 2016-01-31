import java.util.Base64
/**
  * Created by Caleb Prior on 23-Jan-16.
  */
class FileServerMessageHandler(socketHandler: SocketHandler, primaryNode:Boolean, fileManager:FileManager) extends MessageHandler(socketHandler) {
  def handleMessage(msg: String): Unit = {
    MessageTypes.getMessageType(msg) match {
      case MessageTypes.WriteFile => handleWriteFile()
      case MessageTypes.ReadFile => handleReadFile()
      case MessageTypes.None => handleUnknown()
    }
  }

  def handleUnknown(): Unit ={

  }

  def handleWriteFile(): Unit ={
    // Use primary copy model! writes to a primary propagates the writes to replicas
    // Can read from any replica or primary node

    val fileName = socketHandler.readLine().split(':')(1).trim
    val length = Integer.parseInt(socketHandler.readLine().split(':')(1).trim())
    val fileBytes = readInFileBytes(length)

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

  def sendFileToReplicas(fileName: String, fileBytes: Array[Byte]) = {

  }

  def handleReadFile(): Unit = {
    val fileName = socketHandler.readLine().split(':')(1).trim

    if(fileManager.fileExists(fileName)){

      print("file exists - " + fileName)
      val fileBytes = fileManager.getFileBytes(fileName)
      val encodedBytes = Base64.getEncoder.encode(fileBytes)
      socketHandler.sendLines(new Messages.ReadFileResponse(fileName, encodedBytes.length).toString)
      socketHandler.sendBytes(encodedBytes)
    } else {
      socketHandler.sendLines(new Messages.ErrorFileNotFound(fileName).toString)
    }
  }
}
