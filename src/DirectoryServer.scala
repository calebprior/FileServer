import java.net.{Socket, ServerSocket}
import java.util.concurrent.Executors

object DirectoryServer extends ServerTrait{
  def setup(args: Array[String]): Unit = {
    val portNumber = args(0)

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
    pool.execute(new ServerListener(socketHandler, this, new DirectoryMessageHandler(socketHandler)))
  }
}