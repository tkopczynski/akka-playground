package printer

import akka.actor.{Actor, ActorSystem, Props}

object PrinterActor extends App {

  implicit val system = ActorSystem("PrinterActorSystem")
  val localActor = system.actorOf(Props[PrinterActor], name = "PrinterActor")
}

class PrinterActor extends Actor {
  override def receive: Receive = {
    case msg: String => println(s"Printing: $msg")
  }
}
