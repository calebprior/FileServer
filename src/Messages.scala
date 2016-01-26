/**
  * Created by Caleb Prior on 22-Jan-16.
  */
object Messages {
  trait Message {
    def toString: String
  }

  class LookUp(fileName:String, read:Boolean) extends Message {
    override def toString =
      "LOOKUP\n" + "FILENAME:" + fileName + "\nACCESS_TYPE:" + (if(read) "READ" else "FALSE"
  }

  class LookUpResponse(entry: DirectoryEntry) extends Message {
    override def toString =
      "LOOKUP_RESPONSE\n" + "FILENAME:" + entry.getFilePath + "\nFILE_ID:" + entry.getFileId + "\nNODE_ID:" + entry.getFileServerId
  }

  class WriteFile(fileName:String, length:Int) extends Message{
    override def toString =
      "WRITE_FILE\n" + "Filename:" + fileName + "\n" + "Length:" + length
  }

  class ReadFile(fileName:String) extends Message{
    override def toString =
      "READ_FILE\n" + "Filename:" + fileName
  }
}
