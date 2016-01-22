import java.nio.file.{Paths, Files}

/**
  * Created by Caleb Prior on 22-Jan-16.
  */
object FileIOHelper {

  def readFile(fileName: String): Array[Byte] = {
    Files.readAllBytes(Paths.get(fileName))
  }

  def writeFile(fileName: String, bytes: Array[Byte]):Unit = {
    Files.write(Paths.get(fileName), bytes)
  }
}
