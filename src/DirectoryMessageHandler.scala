import java.util.Base64
import Messages.LookUpResponse

/**
  * Created by Caleb Prior on 22-Jan-16.
  */
class DirectoryMessageHandler(socketHandler: SocketHandler) extends MessageHandler(socketHandler){
  var directoryManager = new DirectoryManager()

  def handleMessage(msg: String): Unit = {
    MessageTypes.getMessageType(msg) match {
      case MessageTypes.LookUp => handleLookup()
      case MessageTypes.WriteFile => handleWriteFile()
      case MessageTypes.None => println("WORKER: " + Thread.currentThread.getId + " unknown message")
    }
  }

  def handleLookup():Unit = {
    val fileName = socketHandler.readLine().split(':')(1).trim
    val accessType = socketHandler.readLine().split(':')(1).trim

    println(fileName +  " " + accessType)

    if(directoryManager.hasEntry(fileName)){
      // File Already exists, get an appropriate file server

      val entries = directoryManager.getEntries(fileName)

      if(entries.nonEmpty){
        val message = new Messages.LookUpResponse(entries.head)
        socketHandler.sendLines(message.toString)
      }

    } else {
      // File not there yet, add new one to list and assign a file server for it
      val newEntry = directoryManager.addNewEntry(fileName, 0)
      val message = new LookUpResponse(newEntry)
      socketHandler.sendLines(message.toString)
    }
  }

  def handleWriteFile():Unit = {
    val fileName = socketHandler.readLine().split(':')(1).trim
    val length = Integer.parseInt(socketHandler.readLine().split(':')(1).trim())

    val bytesIn = socketHandler.readBytes(length)

    val bytes = Base64.getDecoder.decode(bytesIn.toString("UTF-8"))

    FileIOHelper.writeFile("testServer", bytes)
  }
}
