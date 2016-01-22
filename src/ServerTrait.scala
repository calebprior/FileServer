import java.net.{Socket, ServerSocket}
import java.util.concurrent.ExecutorService
/**
  * Created by Caleb Prior on 22-Jan-16.
  */
trait ServerTrait {
  var running: Boolean = true
  var poolSize: Int = 15
  var port: Int = -1
  var pool: ExecutorService = null
  var serverSocket:ServerSocket = null

  def setup(args: Array[String]):Unit
  def acceptNewConnection(socket:Socket):Unit

  def main(args: Array[String]): Unit = {
    setup(args)
    println("SERVER: Started on port " + port + " with thread pool size of: " + poolSize)

    while(running){
      try{
        acceptNewConnection(serverSocket.accept())
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

  def shutdown(): Unit = {
    println("SERVER: Closing server socket")
    serverSocket.close()
  }
}
