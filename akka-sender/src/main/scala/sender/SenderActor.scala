package sender

import akka.actor.{Actor, ActorSystem, Props}

import scala.io.StdIn.readLine

object SenderActor extends App {

  implicit val system = ActorSystem("SenderActorSystem")
  val localActor = system.actorOf(Props[SenderActor], name = "SenderActor")

  var done = false
  while (! done) {
    val input = readLine("print > ")

    input match {
      case "exit" => {
        done = true
        system.terminate()
      }
      case input => localActor ! input
    }
  }
}

class SenderActor extends Actor {
  val remoteActor = context.actorSelection("akka.tcp://PrinterActorSystem@127.0.0.1:31337/user/PrinterActor")

  override def receive: Receive = {
    case msg: String => remoteActor ! msg
  }
}