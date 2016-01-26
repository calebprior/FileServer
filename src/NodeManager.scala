import scala.util.Random

/**
  * Created by Caleb Prior on 26-Jan-16.
  */
class NodeManager {
  var primaryNodeList:List[NodeGroup] = List()

  def getRandomPrimary:Node = {
    val rnd = new GenRandInt(100, 200)
    val randomInt = rnd.next
  }

  def getRandomInt(lowerBound:Int, upperBound:Int):Int = {
    val randomNumbers = new Random()
    lowerBound + randomNumbers.nextInt(upperBound - lowerBound + 1 )
  }
}

class NodeGroup(primaryNode: Node) {
  var replicaNodes:List[Node] = List()

  def addNode(newNode:Node): Unit ={
    replicaNodes = newNode :: replicaNodes
  }

  def removeNode(nodeToRemove:Node):Unit = {
    var newList:List[Node] = List()

    if(replicaNodes.nonEmpty){
      for(node <- replicaNodes){
        if(!node.IpAddress().equals(nodeToRemove.IpAddress()) && node.Port() != nodeToRemove.Port()){
          newList = node :: newList
        }
      }
    }

    replicaNodes = newList
  }
}