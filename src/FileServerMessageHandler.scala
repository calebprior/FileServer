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
    if(primaryNode){

    } else {

    }
  }

  def handleReadFile(): Unit ={

  }
}
