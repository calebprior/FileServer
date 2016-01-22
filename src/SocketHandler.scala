import java.io.{ByteArrayOutputStream, PrintStream, InputStream}
import java.net.Socket
import java.nio.charset.Charset

/**
  * Created by Caleb Prior on 22-Jan-16.
  */
class SocketHandler(socket: Socket) {
  var inStream: InputStream = socket.getInputStream
  var outStream: PrintStream = new PrintStream(socket.getOutputStream)

  def readLine(): String = {
    var newLine = ""

    var next = inStream.read()
    while(next != '\n'){
      newLine += next.toChar
      next = inStream.read()
    }
    newLine
  }

  def readByte(): Byte = {
    var byte = Array[Byte](1)
    inStream.read(byte)
    byte(0)
  }

  def readBytes(numBytes: Int): ByteArrayOutputStream = {
    var byteStream = new ByteArrayOutputStream()
    var count = numBytes
    while(count > 0){
      byteStream.write(readByte())
      count -= 1
    }
    byteStream
  }

  def sendLines(line: String): Unit = {
    outStream.println(line)
    outStream.flush()
  }

  def sendBytes(bytes: Array[Byte]): Unit = {
    outStream.write(bytes)
    outStream.flush()
  }

  def hasContent: Boolean = {
    inStream.available() > 0
  }

  def isConnected: Boolean = {
    socket.isConnected
  }

  def isOpen: Boolean = {
    !socket.isClosed
  }

  def close(): Unit = {
    socket.close()
  }

  def getIpAddress: String = {
    socket.getLocalAddress.toString.drop(1)
  }

  def getPort: Int = {
    socket.getLocalPort
  }
}
