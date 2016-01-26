import java.net.InetAddress

/**
  * Created by Caleb Prior on 26-Jan-16.
  */
class Node(ipAddress:InetAddress, port:Int) {
  def IpAddress():InetAddress = {
    ipAddress
  }

  def Port():Int = {
    port
  }
}
