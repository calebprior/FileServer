/**
  * Created by Caleb Prior on 23-Jan-16.
  */
object MessageTypes extends Enumeration {
  type MessageType = Value
  val LookUp, WriteFile, ReadFile, None = Value

  def getMessageType(firstLine:String):MessageTypes.MessageType = {
    if(firstLine.startsWith("LOOKUP")) return MessageTypes.LookUp
    if(firstLine.startsWith("WRITE_FILE")) return MessageTypes.WriteFile
    if(firstLine.startsWith("READ_FILE")) return MessageTypes.ReadFile

    MessageTypes.None
  }
}
