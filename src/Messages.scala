/**
  * Created by Caleb Prior on 22-Jan-16.
  */

trait Message {
  def asString: String
}

object MessageType extends Enumeration {
  type MessageType = Value
  val LookUp, WriteFile, None = Value

  def getMessageType(firstLine:String):MessageType.MessageType = {
    if(firstLine.startsWith("LOOKUP")) return MessageType.LookUp
    if(firstLine.startsWith("WRITE_FILE")) return MessageType.WriteFile

    MessageType.None
  }
}

object Messages {
  class LookUp(fileName:String) extends Message {
    def asString: String = {
      "LOOKUP:"+fileName
    }
  }

  class WriteFile(fileName:String, length:Int) extends Message{
    def asString: String = {
      "WRITE_FILE\n" + "Filename:" + fileName + "\n" + "Length:" + length
    }
  }
}
