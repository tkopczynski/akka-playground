package sender

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout

import scala.io.StdIn.readLine

object SenderActor extends App {

  implicit val system = ActorSystem("SenderActorSystem")

  import system.dispatcher

  implicit val timeout = Timeout(5, TimeUnit.SECONDS)

  val remoteActor = system.actorSelection("akka.tcp://PrinterActorSystem@127.0.0.1:31337/user/PrinterActor")

  println("Type some text. Every new line will get printed in the PrintActor. Use `exit` for ending the session.")

  var done = false
  while (! done) {
    val input = readLine()

    input match {
      case "exit" => {
        done = true
        system.terminate()
      }
      case input: String => {
        val result = (remoteActor ? input).mapTo[Int]
        result.onComplete(response => println(s"Response status code = ${response getOrElse -1}"))
      }
    }
  }
}