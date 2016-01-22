/**
  * Created by Caleb Prior on 22-Jan-16.
  */

trait Message {
  def asString: String
}

object MessageType extends Enumeration {
  type MessageType = Value
  val LookUp, None = Value

  def getMessageType(firstLine:String):MessageType.MessageType = {
    if(firstLine.startsWith("LOOKUP")) return MessageType.LookUp

    MessageType.None
  }
}

class Messages {
  class LookUp(fileName:String) extends Message {
    def asString: String = {
      "LOOKUP:"+fileName
    }
  }

  class WriteFile(fileName:String, length:Int, data:Array[Byte]) extends Message{
    def asString: String = {
      "LOOKUP:"+fileName
    }
  }
}
