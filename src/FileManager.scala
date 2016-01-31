/**
  * Created by Caleb Prior on 26-Jan-16.
  */
class FileManager(rootFolder:String) {
  var fileList: List[File] = List()
  var nextNewId = 0

  def getNextId:Int = {
    this.synchronized {
      nextNewId += 1
      nextNewId
    }
  }

  def getFileBytes(fileName: String): Array[Byte] = {
    for(file <- fileList){
      if(file.FileName.equals(fileName)){
        return FileIOHelper.readFile(rootFolder + file.FileId.toString)
      }
    }
    null
  }

  def writeFile(fileName: String, fileBytes: Array[Byte]) = {
    if(fileExists(fileName)){
      val id = getIdFromFileName(fileName)
      FileIOHelper.writeFile(rootFolder + id.toString, fileBytes)
    } else {
      val newId = addNewFile(fileName)
      FileIOHelper.writeFile(rootFolder + newId.toString, fileBytes)
    }
  }

  def getIdFromFileName(fileName:String): Int = {
    for(file <- fileList){
      if(file.FileName.equals(fileName)){
        return file.FileId
      }
    }
    0
  }

  def addNewFile(fileName:String):Int = {
    val newFile = new File(fileName, getNextId)
    fileList = newFile :: fileList
    newFile.FileId
  }

  def fileExists(fileName: String): Boolean = {
    fileList.nonEmpty && fileList.exists(file => file.FileName.equals(fileName))
  }

  class File(fileName:String, fileId:Int){
    def FileName:String = fileName
    def FileId:Int = fileId
  }
}
