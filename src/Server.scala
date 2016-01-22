import java.net.ServerSocket
import java.util.concurrent.{Executors, ExecutorService}

trait ServerTrait {
  def shutdown():Unit
}

object Server extends ServerTrait{
  var running: Boolean = true
  var poolSize: Int = 15
  var port: Int = -1
  var pool: ExecutorService = null
  var serverSocket:ServerSocket = null

  def main (args: Array[String]): Unit ={
    Setup(args)
    println("SERVER: Started on port " + port + " with thread pool size of: " + poolSize)

    while(running){
      try{
        val socketHandler = new SocketHandler(serverSocket.accept())
        println("CHAT SERVER: Connection Received")
        pool.execute(new ServerListener(socketHandler, this))
      } catch {
        case e: Exception =>
          println("CHAT SERVER: Shutting down")
          running = false
          pool.shutdown()
      }
    }

    if (!serverSocket.isClosed && serverSocket != null) {
      serverSocket.close()
    }

    System.exit(0)
  }


  def Setup(args: Array[String]): Unit = {
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

  def shutdown(): Unit = {
    println("SERVER: Closing server socket")
    serverSocket.close()
  }
}
