import java.net.{ServerSocket, Socket}
import java.util.concurrent.Executors

/**
  * Created by Caleb Prior on 23-Jan-16.
  */
class FileServer extends ServerTrait{
  var primaryNode:Boolean = false

  def setup(args: Array[String]): Unit = {
    val portNumber = args(0)
    primaryNode = Integer.parseInt(args(1)) > 0

    try{
      port = Integer.parseInt(portNumber)
      serverSocket = new ServerSocket(port)
      pool = Executors.newFixedThreadPool(poolSize)
    } catch {
      case e : Exception =>
        println("SERVER: ERROR - " + e.getMessage)
        System.exit(0)
    }
  }

  def acceptNewConnection(socket: Socket): Unit = {
    val socketHandler = new SocketHandler(socket)
    pool.execute(new ServerListener(socketHandler, this, new FileServerMessageHandler(socketHandler, primaryNode)))
  }
}

