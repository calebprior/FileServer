/**
  * Created by Caleb Prior on 22-Jan-16.
  */
abstract class MessageHandler(socketHandler: SocketHandler) {
  def handleMessage(msg: String): Unit
}