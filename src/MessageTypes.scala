/**
  * Created by Caleb Prior on 23-Jan-16.
  */
object MessageTypes extends Enumeration {
  type MessageType = Value
  val LookUp, LookUpResponse, WriteFile, WriteFileResponse, ReadFile, None = Value

  def getMessageType(firstLine:String):MessageTypes.MessageType = {
    if(firstLine.startsWith("LOOKUP")) return MessageTypes.LookUp
    if(firstLine.startsWith("LOOKUP_RESPONSE")) return MessageTypes.LookUpResponse
    if(firstLine.startsWith("WRITE_FILE")) return MessageTypes.WriteFile
    if(firstLine.startsWith("WRITE_FILE_RESPONSE")) return MessageTypes.WriteFileResponse
    if(firstLine.startsWith("READ_FILE")) return MessageTypes.ReadFile

    MessageTypes.None
  }
}
