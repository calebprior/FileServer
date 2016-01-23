/**
  * Created by Caleb Prior on 23-Jan-16.
  */
class DirectoryManager {
  var directory: List[DirectoryEntry] = List()
  var nextNewId = 0

  def getNextId:Int = {
    this.synchronized {
      nextNewId += 1
      nextNewId
    }
  }

  def addNewEntry(fileName:String, nodeId:Int): DirectoryEntry ={
    var newEntry = new DirectoryEntry(fileName, getNextId, nodeId)
    directory = newEntry :: directory
    newEntry
  }

  def addEntry(fileName:String, fileId:Int, nodeId:Int): DirectoryEntry = {
    var newEntry = new DirectoryEntry(fileName, fileId, nodeId)
    directory = newEntry :: directory
    newEntry
  }

  def deleteEntry(): Unit = {

  }

  def hasEntry(fileName:String): Boolean = {
    directory.nonEmpty && directory.exists(entry => entry.getFilePath.equals(fileName))
  }

  /**
    * Get all the entries (i.e list of fileServers) for a particular file
    * @param fileName the fileName being requested
    * @return list of Directory Entries (may be empty)
    */
  def getEntries(fileName:String): List[DirectoryEntry] = {
    var returnList: List[DirectoryEntry] = List()
    if(directory.nonEmpty){
      for(entry <- directory){
        if(entry.getFilePath.equals(fileName)){
          returnList = entry :: returnList
        }
      }
    }
    returnList
  }
}
