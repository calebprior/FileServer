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
    var fileName = socketHandler.readLine().split(':')(1).trim
    var accessType = socketHandler.readLine().split(':')(1).trim

    println(fileName +  " " + accessType)

    if(directoryManager.hasEntry(fileName)){
      // File Already exists, get an appropriate file server

      var entries = directoryManager.getEntries(fileName)

      if(entries.nonEmpty){
        var message = new Messages.LookUpResponse(entries.head)
        socketHandler.sendLines(message.toString)
      }

    } else {
      // File not there yet, add new one to list and assign a file server for it
      var newEntry = directoryManager.addNewEntry(fileName, 0)
      var message = new LookUpResponse(newEntry)
      socketHandler.sendLines(message.toString)
    }
  }

  def handleWriteFile():Unit = {
    var fileName = socketHandler.readLine().split(':')(1).trim
    var length = Integer.parseInt(socketHandler.readLine().split(':')(1).trim())

    var bytesIn = socketHandler.readBytes(length)

    var bytes = Base64.getDecoder.decode(bytesIn.toString("UTF-8"))

    FileIOHelper.writeFile("testServer", bytes)
  }
}
