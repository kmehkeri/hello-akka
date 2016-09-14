import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._

object HelloAkka {
  def main(args: Array[String]) {
    val host = "localhost"
    val port = 8080

    implicit val actorSystem = ActorSystem("hello-system")
    implicit val actorMaterializer = ActorMaterializer()
    implicit val executionContext = actorSystem.dispatcher

    val route = pathSingleSlash {
      get {
        complete {
          "Hello Akka!"
        }
      }
    }

    val bindingFuture = Http().bindAndHandle(route, host, port)
    println(s"Server started at ${host}:${port}\nPress ENTER to stop...")
    readLine()

    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => actorSystem.terminate()) // and shutdown when done
  }
}

